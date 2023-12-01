package com.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @ManyToOne
    private Product product;

    @Positive
    private Integer count;
}