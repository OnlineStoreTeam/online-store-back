package com.store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "products")
public class Product {
    @Id
    private Long id;
    private int article;
    private String name;
    private BigDecimal price;
    private String category;
    private String description;
    private int quantity;
    private ProductStatus isDeleted;
}
