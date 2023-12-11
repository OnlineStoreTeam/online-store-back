package com.store.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FavouriteItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String userId;
}