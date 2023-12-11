package com.store.dto;

import lombok.Data;

@Data
public class FavouriteItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String userId;
}