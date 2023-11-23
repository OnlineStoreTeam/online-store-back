package com.store.dto.cartDTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartDTO {
    private Long id;
    private String userId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer count;

}