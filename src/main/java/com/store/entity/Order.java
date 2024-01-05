package com.store.entity;

import com.store.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String number;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private ZonedDateTime createdDate;
    private BigDecimal price;
    private Integer count;
    private String userId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList;
    private String shippingAddress;
    private String paymentType;
    private ZonedDateTime purchasedDate;
}