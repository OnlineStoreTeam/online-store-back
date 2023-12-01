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
    private final OrderService orderService;

    @GetMapping("order/{orderNumber}")
    public OrderDTO getOrderByUserId(@PathVariable String orderNumber, Principal principal, String optionalUserIdIfNotAuthenticated) {
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }
        return orderService.getOrderByUserIdAndOrderNumber(id, orderNumber);
    }
}