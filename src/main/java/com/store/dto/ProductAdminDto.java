package com.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.entity.Product;
import com.store.entity.ProductStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Article is required")
    @Column(unique = true)
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Article should only contain alphanumeric characters")
    @Size(min = 3, max = 8, message = "Article should be between 3 and 8 characters long")
    private String article;
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z0-9 _,.-]+$", message = "Name should only contain alphanumeric characters")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters long")
    private String name;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;
    @NotBlank(message = "Category is required")
    private String category;
    @NotBlank(message = "Description is required")
    @Pattern(regexp = "^[a-zA-Z0-9 '&!#%()*+,.:;_-]+$", message = "Description should only contain alphanumeric characters")
    @Size(min = 2,max = 255, message = "Description should be between 2 and 255 characters long")
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
