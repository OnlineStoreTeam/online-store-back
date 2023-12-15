package com.store.service;

import com.store.dto.orderDTOs.OrderDTO;
import com.store.dto.orderDTOs.OrderItemDTO;
import com.store.enums.OrderStatus;
import com.store.exception.DataNotFoundException;
import com.store.mapper.OrderMapper;
import com.store.repository.OrderRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

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

    public Page<OrderDTO> getAllOrdersByUserId(String userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable).map(orderMapper::toDto);
    }



    public void cancelOrder(String userId, String orderNumber) {
        if (!orderRepository.existsByNumberAndUserId(orderNumber, userId)) {
            throw new DataNotFoundException("There is no order with number " + orderNumber + " for current logged user");
        }
        OrderDTO orderDTO = orderMapper.toDto(orderRepository.findOrderByUserIdAndNumber(userId, orderNumber));

        orderDTO.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(orderMapper.toEntity(orderDTO));
    }

    public void confirmOrder(String orderNumber, String userId) {
        if (!orderRepository.existsByNumberAndUserId(orderNumber, userId)) {
            throw new DataNotFoundException("There is no order for current logged user with number " + orderNumber);
        }

        OrderDTO orderDTO = orderMapper.toDto(orderRepository.findOrderByUserIdAndNumber(userId, orderNumber));
        orderDTO.setStatus(OrderStatus.DONE);
        orderDTO.setPurchasedDate(ZonedDateTime.now());

        orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDTO)));
    }

    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }

    public void exportAllOrdersToCsv(HttpServletResponse response) throws IOException {
        List<OrderDTO> orderDTOList = orderMapper.toDto(orderRepository.findAll());

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=orders.csv");

        String[] headers = {"Number", "UserId", "Status", "Created Date", "Product Name", "Product Count","Total Price", "Total Count",
                "Shipping Address", "Payment type","Purchased Date"};

        try(CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.POSTGRESQL_CSV.builder().setHeader(headers).build())) {
            for(OrderDTO orderDTO : orderDTOList) {
                for(OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOList()){
                    csvPrinter.printRecord(
                           orderDTO.getNumber(),
                            orderDTO.getUserId(),
                            orderDTO.getStatus(),
                            orderDTO.getCreatedDate(),
                            orderItemDTO.getProductName(),
                            orderItemDTO.getCount(),
                            orderDTO.getPrice(),
                            orderDTO.getCount(),
                            orderDTO.getShippingAddress(),
                            orderDTO.getPaymentType(),
                            orderDTO.getPurchasedDate()
                    );
                }
            }
        }
    }
}