package com.store.dto.orderDTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductDTO {
    private Long id;
    private String orderNumber;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer count;
}