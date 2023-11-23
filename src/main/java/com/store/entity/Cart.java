package com.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Entity
@Data
public class Cart {
    private static final String SEQ_NAME = "cart_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    private String userId;

    @ManyToOne
    private Product product;

    @Positive
    private Integer count;
}