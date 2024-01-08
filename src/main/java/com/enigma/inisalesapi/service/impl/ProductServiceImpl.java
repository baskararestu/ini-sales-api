package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.dto.request.ProductRequest;
import com.enigma.inisalesapi.dto.response.CategoryResponse;
import com.enigma.inisalesapi.dto.response.ProductResponse;
import com.enigma.inisalesapi.entity.Category;
import com.enigma.inisalesapi.entity.Product;
import com.enigma.inisalesapi.entity.ProductDetail;
import com.enigma.inisalesapi.entity.ProductPrice;
import com.enigma.inisalesapi.exception.NotFoundException;
import com.enigma.inisalesapi.exception.ProductAlreadyExistsException;
import com.enigma.inisalesapi.exception.ProductInactiveException;
import com.enigma.inisalesapi.repository.ProductRepository;
import com.enigma.inisalesapi.service.CategoryService;
import com.enigma.inisalesapi.service.ProductDetailService;
import com.enigma.inisalesapi.service.ProductPriceService;
import com.enigma.inisalesapi.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailService productDetailService;
    private final ProductPriceService productPriceService;
    private final CategoryService categoryService;
    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        CategoryResponse category = categoryService.getCategoryById(productRequest.getCategoryId());
      Optional<Product> checkerData=  productRepository.findProductByProductDetailNameAndProductDetailCategoryName
                (productRequest.getProductName(),category.getCategoryName());
        if(checkerData.isEmpty()){
            ProductDetail savedDetail = ProductDetail.builder()
                    .name(productRequest.getProductName())
                    .description(productRequest.getDescription())
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .category(Category.builder()
                            .id(category.getId())
                            .build())
                    .build();

            ProductDetail productDetail = productDetailService.createProductDetail(savedDetail);
            ProductPrice savedPrice = ProductPrice.builder()
                    .price(productRequest.getPrice())
                    .stock(productRequest.getStock())
                    .createdAt(LocalDateTime.now())
                    .isActive(true)
                    .build();

            ProductPrice productPrice = productPriceService.create(savedPrice);
            String productId = UUID.randomUUID().toString();
            productRepository.insertProductNative(productId, true,productDetail.getId(), productPrice.getId());

            return ProductResponse.builder()
                    .productId(productId)
                    .productName(productRequest.getProductName())
                    .price(productRequest.getPrice())
                    .productDescription(productRequest.getDescription())
                    .stock(productRequest.getStock())
                    .productCategory(category)
                    .build();
        }else {
            throw new ProductAlreadyExistsException(
                    "Product with name '" + productRequest.getProductName() +
                            "' with category '" + category.getCategoryName() +
                            "' already exists.");
        }
    }

    @Override
    public ProductResponse getById(String id) {
      Optional<Product> product=  productRepository.findById(id);

        ProductDetail getDetail = product.get().getProductDetail();
        ProductPrice getPrice = product.get().getProductPrice();
        return ProductResponse.builder()
                .productName(getDetail.getName())
                .productCategory(CategoryResponse.builder()
                        .categoryName(getDetail.getCategory().getName())
                        .build())
                .productDescription(getDetail.getDescription())
                .price(getPrice.getPrice())
                .stock(getPrice.getStock())
                .build();
    }

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        return null;
    }

    @Override
    @Transactional
    public void delete(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found."));

        ProductDetail productDetail = product.getProductDetail();
        if (productDetail.getIsActive()) {
            productRepository.softDeleteProduct(productDetail.getId());
            productPriceService.deleteById(product.getProductPrice().getId());
            productDetailService.deleteById(productDetail.getId());
        } else {
            throw new ProductInactiveException("Product detail is already inactive.");
        }
    }


    @Override
    @Transactional
    public ProductResponse updateProduct(String productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found."));

        ProductDetail existingDetail = product.getProductDetail();
        if (existingDetail.getIsActive()) {
            if (!existingDetail.getName().equals(productRequest.getProductName())
                    || !existingDetail.getDescription().equals(productRequest.getDescription())
                    || !existingDetail.getCategory().getId().equals(productRequest.getCategoryId())) {

                existingDetail.setIsActive(false);
                productDetailService.update(existingDetail);

                ProductDetail newDetail = ProductDetail.builder()
                        .name(productRequest.getProductName())
                        .description(productRequest.getDescription())
                        .isActive(true)
                        .createdAt(LocalDateTime.now())
                        .category(Category.builder()
                                .id(productRequest.getCategoryId())
                                .build())
                        .build();
                ProductDetail savedNewDetail = productDetailService.createProductDetail(newDetail);

                product.setProductDetail(savedNewDetail);
                productRepository.save(product);

                return ProductResponse.builder()
                        .productId(product.getId())
                        .productName(savedNewDetail.getName())
                        .price(product.getProductPrice().getPrice())
                        .productDescription(savedNewDetail.getDescription())
                        .stock(product.getProductPrice().getStock())
                        .productCategory(CategoryResponse.builder()
                                .categoryName(savedNewDetail.getCategory().getName())
                                .build())
                        .build();
            } else {
                throw new ProductAlreadyExistsException("Data is the same. No changes made.");
            }
        } else {
            throw new ProductInactiveException("Product detail is already inactive.");
        }
    }

    public boolean getByNameAndCategory(String productName, String categoryId) {
        Optional<Product> products = productRepository.findByNameAndCategory(productName, categoryId);
        return products.isEmpty();
    }
}
