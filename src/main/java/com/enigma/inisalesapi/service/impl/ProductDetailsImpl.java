package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.entity.Category;
import com.enigma.inisalesapi.entity.ProductDetail;
import com.enigma.inisalesapi.repository.ProductDetailRepository;
import com.enigma.inisalesapi.service.CategoryService;
import com.enigma.inisalesapi.service.ProductDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductDetailsImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;
    @Override
    @Transactional
    public ProductDetail createProductDetail(ProductDetail productDetail) {
        String detailId= UUID.randomUUID().toString();
        productDetailRepository.insertProductDetailNative
                (detailId,productDetail.getName(),productDetail.getDescription(),true,
                        LocalDateTime.now(),productDetail.getCategory().getId());
        Optional<ProductDetail> createdDetail = getById(detailId);
        ProductDetail getDetail = createdDetail.get();
        return ProductDetail.builder()
                .id(getDetail.getId())
                .name(getDetail.getName())
                .description(getDetail.getDescription())
                .isActive(getDetail.getIsActive())
                .createdAt(getDetail.getCreatedAt())
                .category(Category.builder()
                        .id(getDetail.getCategory().getId())
                        .build())
                .build();
    }

    @Override
    public Optional<ProductDetail> getById(String id) {
      return   productDetailRepository.findProductDetailByIdNative(id);
    }

    @Override
    public void deleteById(String id){
        Optional<ProductDetail> detail = productDetailRepository.findProductDetailByIdNative(id);
        detail.ifPresent(d->{
            productDetailRepository.softDeleteProduct(d.getId());
        });
    }
}
