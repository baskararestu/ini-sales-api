package com.enigma.inisalesapi.service;

import com.enigma.inisalesapi.entity.ProductDetail;

public interface ProductDetailService {
    ProductDetail createProductDetail(ProductDetail productDetail);
    ProductDetail getProductDetailById(String id);
}
