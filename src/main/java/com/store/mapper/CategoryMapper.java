package com.store.mapper;

import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDTO toDto(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
