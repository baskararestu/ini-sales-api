package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.dto.request.CategoryRequest;
import com.enigma.inisalesapi.dto.response.CategoryResponse;
import com.enigma.inisalesapi.entity.Category;
import com.enigma.inisalesapi.repository.CategoryRepository;
import com.enigma.inisalesapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
   Category saved =
           categoryRepository.insertCategoryNative(
                   categoryRequest.getCategoryName(),
                   LocalDateTime.now()
        );
        return CategoryResponse.builder()
                .id(saved.getId())
                .categoryName(saved.getName())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
