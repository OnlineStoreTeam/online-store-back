package com.store.mapper;

import com.store.dto.categoryDTOs.CategoryCreateDTO;
import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.dto.categoryDTOs.CategoryUpdateDTO;
import com.store.entity.Category;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryDTO toDto(Category category);

    List<CategoryDTO> toDto(Page<Category> categoryPage);

    Category toEntity(CategoryCreateDTO categoryCreateDTO);

    Category toEntity(CategoryUpdateDTO categoryUpdateDTO);
}