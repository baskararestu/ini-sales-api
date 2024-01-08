package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.entity.ProductPrice;
import com.enigma.inisalesapi.repository.ProductPriceRepository;
import com.enigma.inisalesapi.service.ProductPriceService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductPriceImpl implements ProductPriceService {
    private final ProductPriceRepository productPriceRepository;
    private final EntityManager entityManager;
    @Override
    @Transactional
    public ProductPrice create(ProductPrice productPrice) {
        String priceId=UUID.randomUUID().toString();
        productPriceRepository.insertProductPriceNative
                (priceId,true,productPrice.getPrice(),productPrice.getStock(),LocalDateTime.now());
        ProductPrice getCreatedPrice=getById(priceId);
        return ProductPrice.builder()
                .id(getCreatedPrice.getId())
                .price(getCreatedPrice.getPrice())
                .stock(getCreatedPrice.getStock())
                .createdAt(getCreatedPrice.getCreatedAt())
                .isActive(getCreatedPrice.isActive())
                .build();
    }

    @Override
    public ProductPrice getById(String id) {
        return productPriceRepository.findProductPricesByIdNative(id);
    }
}
