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
        ProductDetail createdDetail = getProductDetailById(detailId);
        return ProductDetail.builder()
                .id(createdDetail.getId())
                .name(createdDetail.getName())
                .description(createdDetail.getDescription())
                .isActive(createdDetail.getIsActive())
                .createdAt(createdDetail.getCreatedAt())
                .category(Category.builder()
                        .id(createdDetail.getCategory().getId())
                        .build())
                .build();
    }

    @Override
    public ProductDetail getProductDetailById(String id) {
      return   productDetailRepository.findProductDetailByIdNative(id);
    }
}
