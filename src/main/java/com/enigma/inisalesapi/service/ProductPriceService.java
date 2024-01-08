package com.enigma.inisalesapi.service;

import com.enigma.inisalesapi.entity.ProductPrice;

public interface ProductPriceService {
    ProductPrice create(ProductPrice productPrice);

    ProductPrice getById(String id);
}
