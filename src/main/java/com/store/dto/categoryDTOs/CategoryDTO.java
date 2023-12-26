package com.store.dto.categoryDTOs;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String title;
    private String path;
    private long productCount;
}