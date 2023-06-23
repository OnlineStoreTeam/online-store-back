package com.store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "products")
public class Product {
    private static final String SEQ_NAME="product_seq";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    private int article;
    private String name;
    private BigDecimal price;
    private String category;
    private String description;
    private int quantity;
    private ProductStatus isDeleted;
}
