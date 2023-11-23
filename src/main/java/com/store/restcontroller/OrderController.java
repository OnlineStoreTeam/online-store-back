package com.store.restcontroller;

import com.store.dto.orderDTOs.OrderDTO;
import com.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private OrderService orderService;

    @GetMapping("order/{orderNumber}")
    public OrderDTO getOrderByUserId(Principal principal, @PathVariable String orderNumber) {
        String userId = principal.getName();
        if (userId == null) {
            userId = "non-user";
        }
        return orderService.getOrderByUserIdAndOrderNumber(userId, orderNumber);
    }
}