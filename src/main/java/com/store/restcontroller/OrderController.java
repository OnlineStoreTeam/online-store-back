package com.store.restcontroller;

import com.store.constants.Role;
import com.store.dto.orderDTOs.OrderDTO;
import com.store.service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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

    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    @GetMapping("orders/export")
    public void exportAllOrders(HttpServletResponse response) throws IOException {
        orderService.exportAllOrdersToCsv(response);
    }

    @GetMapping("orders")
    public Page<OrderDTO> getAllOrdersByUserId(Principal principal, Pageable pageable) {
        return orderService.getAllOrdersByUserId(principal.getName(), pageable);
    }

    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    @GetMapping("orders/all")
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @DeleteMapping("order/{orderNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable String orderNumber, Principal principal) {
        orderService.cancelOrder(principal.getName(), orderNumber);
    }

    @PutMapping("order/confirm/{orderNumber}")
    public void confirmOrder(@PathVariable String orderNumber, Principal principal) {
        orderService.confirmOrder(orderNumber, principal.getName());
    }
}