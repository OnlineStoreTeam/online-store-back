package com.store.dto;

import com.store.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotNull(message = "Id is required")
    private Long id;
    @NotNull(message = "Article is required")
    private int article;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be a positive number")
    private BigDecimal price;
    @NotBlank(message = "Category is required")
    private String category;
    @NotBlank(message = "Description is required")
    private String description;
    @PositiveOrZero(message = "Price must be a positive number")
    private int quantity;
    @NotNull(message = "ProductStatus is required")
    private ProductStatus productStatus;
}
