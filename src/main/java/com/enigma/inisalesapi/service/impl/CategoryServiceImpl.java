package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.dto.request.CategoryRequest;
import com.enigma.inisalesapi.dto.response.AdminResponse;
import com.enigma.inisalesapi.dto.response.CategoryResponse;
import com.enigma.inisalesapi.entity.Category;
import com.enigma.inisalesapi.repository.CategoryRepository;
import com.enigma.inisalesapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
private final CategoryRepository categoryRepository;


    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        String categoryId = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();

        categoryRepository.insertCategoryNative(
                categoryId,
                categoryRequest.getCategoryName(),
                createdAt
        );

        return CategoryResponse.builder()
                .id(categoryId)
                .categoryName(categoryRequest.getCategoryName())
                .createdAt(createdAt)
                .build();
    }


    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = categoryRepository.findByIdNative(id);
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getName())
                .createdAt(category.getCreatedAt())
                .build();
    }

    @Override
    public Page<CategoryResponse> getAllByName(String name, Integer page, Integer size) {
        Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, size));
        return categoryPage.map(category -> CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getName())
                .createdAt(category.getCreatedAt())
                .build());
    }
}
