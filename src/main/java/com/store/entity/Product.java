package com.store.entity;

import com.store.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Article should only contain alphanumeric characters")
    @Size(min = 3, max = 8, message = "Article should be between 3 and 8 characters long")
    private String article;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9 _,.-]+$", message = "Name should only contain alphanumeric characters")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters long")
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotBlank
    private String category;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9 '&!#%()*+,.:;_-]+$", message = "Description should only contain alphanumeric characters")
    @Size(min = 2, max = 255, message = "Description should be between 2 and 255 characters long")
    private String description;

    @PositiveOrZero
    private int quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @NotNull
    private String imagePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}