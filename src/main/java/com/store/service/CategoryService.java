package com.store.service;


import com.store.dto.productDTOs.CategoryDTO;
import com.store.entity.Category;
import com.store.exception.CategoryNotFoundException;
import com.store.mapper.CategoryMapper;
import com.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDTO getCategoryById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) return categoryMapper.toDto(category.get());
        else throw new CategoryNotFoundException("Category with id " + categoryId + "was not found");
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
