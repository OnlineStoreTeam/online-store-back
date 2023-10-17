package com.store.dto.productDTOs;

import com.store.enums.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String article;
    private String name;
    private BigDecimal price;
    private Long categoryId;
    private String description;
    private int quantity;
    private ProductStatus productStatus;
    private String imagePath;
}