package com.store.dto.orderDTOs;

import com.store.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private String number;
    private OrderStatus status;
    private ZonedDateTime createdDate;
    private BigDecimal price;
    private Integer count;
    private String userId;
    private List<OrderProductDTO> orderProductDTOList;
    private ZonedDateTime purchasedDate;
    private String shippingAddress;
    private String paymentType;
}