package com.store.service;


import com.store.dto.categoryDTOs.CategoryCreateDTO;
import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.dto.categoryDTOs.CategoryUpdateDTO;
import com.store.entity.Category;
import com.store.exception.DataNotFoundException;
import com.store.mapper.CategoryMapper;
import com.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDTO getCategoryById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) return categoryMapper.toDto(category.get());
        else throw new DataNotFoundException("There is no category with id " + categoryId);
    }

    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toDto);
    }

    public CategoryDTO createCategory(CategoryCreateDTO categoryCreateDTO) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryCreateDTO)));
    }

    public CategoryDTO updateCategory(CategoryUpdateDTO categoryUpdateDTO) {
        if (!categoryRepository.existsById(categoryUpdateDTO.getId())) {
            throw new DataNotFoundException("There is no category with id " + categoryUpdateDTO.getId());
        }
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryUpdateDTO)));
    }

    public void deleteCategory(Long id) {
        long numberOfProductInCategory = categoryRepository.countItemsByCategory(id);
        if (!categoryRepository.existsById(id)) {
            throw new DataNotFoundException("There is no category with id " + id);
        }
        if (numberOfProductInCategory > 0) {
            throw new DataNotFoundException
                    ("There are " + numberOfProductInCategory + " items in category with id " + id);
        }

        categoryRepository.deleteById(id);
    }
}