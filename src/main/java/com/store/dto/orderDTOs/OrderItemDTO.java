package com.store.dto.orderDTOs;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private String orderNumber;
    private Long productId;
    private String productName;
    private String productPrice;
    private Integer count;
}