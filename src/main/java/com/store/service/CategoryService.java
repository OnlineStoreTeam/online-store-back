package com.store.service;


import com.store.dto.categoryDTOs.CategoryCreateDTO;
import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.dto.categoryDTOs.CategoryUpdateDTO;
import com.store.exception.DataNotFoundException;
import com.store.exception.InvalidDataException;
import com.store.mapper.CategoryMapper;
import com.store.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDTO getCategoryById(Long categoryId) {
        CategoryDTO categoryDTO = categoryMapper.toDto(categoryRepository.findById(categoryId).orElseThrow(() ->
                new DataNotFoundException("There is no category with id " + categoryId)));
        categoryDTO.setProductCount(categoryRepository.countItemsByCategory(categoryId));
        return categoryDTO;
    }

    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toDto);
    }

    public CategoryDTO createCategory(CategoryCreateDTO categoryCreateDTO) {
        try {
            return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryCreateDTO)));
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Please, check for duplicate entries");
        }
    }

    public CategoryDTO updateCategory(CategoryUpdateDTO categoryUpdateDTO) {
        if (!categoryRepository.existsById(categoryUpdateDTO.getId())) {
            throw new DataNotFoundException("There is no category with id " + categoryUpdateDTO.getId());
        }
        try {
            return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryUpdateDTO)));
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Please, check for duplicate entries");
        }
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