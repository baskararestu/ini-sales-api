package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.dto.request.ProductRequest;
import com.enigma.inisalesapi.dto.response.CategoryResponse;
import com.enigma.inisalesapi.dto.response.ProductResponse;
import com.enigma.inisalesapi.entity.Category;
import com.enigma.inisalesapi.entity.Product;
import com.enigma.inisalesapi.entity.ProductDetail;
import com.enigma.inisalesapi.entity.ProductPrice;
import com.enigma.inisalesapi.exception.ProductAlreadyExistsException;
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
      Optional<Product> checker=  productRepository.findProductByProductDetailNameAndProductDetailCategoryName
                (productRequest.getProductName(),category.getCategoryName());
//        Optional<Product> checker=  productRepository.findByNameAndCategory(productRequest.getProductName(), category.getCategoryName());
        if(checker.isEmpty()){
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
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {
            productRepository.softDeleteProduct(p.getId());
            productPriceService.deleteById(p.getProductPrice().getId());
            productDetailService.deleteById(p.getProductDetail().getId());
        });
    }


    @Override
    @Transactional
    public ProductResponse updateProduct(String productId, ProductRequest productRequest) {
        return null;
    }

    public boolean getByNameAndCategory(String productName, String categoryId) {
        Optional<Product> products = productRepository.findByNameAndCategory(productName, categoryId);
        return products.isEmpty();
    }
}
