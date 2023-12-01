package com.store.service;

import com.store.dto.orderDTOs.OrderDTO;
import com.store.exception.DataNotFoundException;
import com.store.mapper.OrderMapper;
import com.store.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderDTO getOrderByUserIdAndOrderNumber(String userId, String orderNumber) {
        if (!orderRepository.existsByNumberAndUserId(orderNumber, userId)) {
            throw new DataNotFoundException("There is no order with number " + orderNumber + " for current logged user");
        }
        return orderMapper.toDto(orderRepository.findOrderByUserIdAndNumber(userId, orderNumber));
    }
}