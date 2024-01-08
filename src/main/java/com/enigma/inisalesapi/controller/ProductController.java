package com.enigma.inisalesapi.controller;

import com.enigma.inisalesapi.constant.AppPath;
import com.enigma.inisalesapi.dto.request.CategoryRequest;
import com.enigma.inisalesapi.dto.request.ProductRequest;
import com.enigma.inisalesapi.dto.response.CategoryResponse;
import com.enigma.inisalesapi.dto.response.ProductResponse;
import com.enigma.inisalesapi.exception.ProductAlreadyExistsException;
import com.enigma.inisalesapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.enigma.inisalesapi.mapper.ResponseControllerMapper.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.PRODUCT)
public class ProductController {
    private final ProductService productService;
    private String message;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            ProductResponse productResponse = productService.createProduct(productRequest);
            message = "Successfully create product";
            return getResponseEntity(message, HttpStatus.CREATED, productResponse);
        } catch (ProductAlreadyExistsException e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            message = "Failed to create product.";
            return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}

