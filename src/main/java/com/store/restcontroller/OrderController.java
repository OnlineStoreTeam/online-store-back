package com.store.restcontroller;

import com.store.dto.orderDTOs.OrderDTO;
import com.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://onlinestoreteam.github.io/products")
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