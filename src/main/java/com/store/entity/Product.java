package com.store.entity;

import com.store.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product {
    private static final String SEQ_NAME="product_id_seq";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
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

    @NotNull
    @ManyToOne
    private Category category;

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
}