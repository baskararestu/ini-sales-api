package com.enigma.inisalesapi.service;


import com.enigma.inisalesapi.dto.request.CategoryRequest;
import com.enigma.inisalesapi.dto.response.CategoryResponse;
import com.enigma.inisalesapi.entity.Category;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
}
