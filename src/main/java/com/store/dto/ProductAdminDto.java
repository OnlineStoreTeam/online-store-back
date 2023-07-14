package com.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.entity.Product;
import com.store.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdminDto {
    @JsonIgnore
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
    private String imagePath;
    @JsonIgnore
    private MultipartFile ImageFile;

    public static ProductAdminDto fromEntity(Product product) {
        return new ProductAdminDto()
                .setArticle(product.getArticle())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setCategory(product.getCategory())
                .setDescription(product.getDescription())
                .setQuantity(product.getQuantity())
                .setProductStatus(product.getProductStatus())
                .setImagePath(product.getImagePath());
    }
}
