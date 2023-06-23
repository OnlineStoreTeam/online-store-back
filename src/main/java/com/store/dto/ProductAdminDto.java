package com.store.dto;

import com.store.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdminDto {
    private int article;
    private String name;
    private BigDecimal price;
    private String category;
    private String description;
    private int quantity;
    private ProductStatus productStatus;
}
