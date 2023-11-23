package com.store.service;

import com.store.dto.cartDTOs.CartDTO;
import com.store.dto.orderDTOs.OrderDTO;
import com.store.dto.orderDTOs.OrderItemDTO;
import com.store.enums.OrderStatus;
import com.store.exception.DataNotFoundException;
import com.store.exception.InvalidDataException;
import com.store.mapper.CartMapper;
import com.store.mapper.OrderMapper;
import com.store.repository.CartRepository;
import com.store.repository.OrderRepository;
import com.store.utils.OrderNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CartAndOrderCreationService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;


    public List<CartDTO> getAllCarts(String userId) {
        return cartMapper.toDto(cartRepository.findAllByUserId(userId));
    }

    public CartDTO addProductToCart(Long productId, String userId) {
//        if (!cartRepository.existsByProductIdAndUserId(productId, userId)) {
//            throw new InvalidDataException("Item with id" + productId + "is already in your cart");
//        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserId(userId);
        cartDTO.setProductId(productId);
        cartDTO.setCount(1);


        return cartMapper.toDto(cartRepository.save(cartMapper.toEntity(cartDTO)));
    }

    public CartDTO updateCountOfProduct(Long productId, Integer count, String userId) {
        if (!cartRepository.existsByProductIdAndUserId(productId, userId)) {
            throw new DataNotFoundException("There is no cart for user with id " + userId + " and items with id " + productId);
        }
        if (!(count > 1)) {
            throw new InvalidDataException("Count can't be less than 1, please check input data");
        }
        CartDTO cartDTO = cartMapper.toDto(cartRepository.findCartByProductIdAndUserId(productId, userId));

        cartDTO.setCount(count);

        return cartMapper.toDto(cartRepository.save(cartMapper.toEntity(cartDTO)));
    }

    public void deleteCartByItemId(Long productId, String userId) {
        if (!cartRepository.existsByProductIdAndUserId(productId, userId)) {
            throw new DataNotFoundException("There is no items in cart with id " + productId + "for current logged user");
        }
        cartRepository.deleteCartByProductId(productId);
    }


    public OrderDTO createOrderFromCart(String shippingAddress, String paymentType, String userId) {
        if (!cartRepository.existsAllByUserId(userId)) {
            throw new DataNotFoundException("There is no cart for current logged user");
        }

        Integer count = 0;
        BigDecimal price = BigDecimal.ZERO;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNumber(OrderNumberGenerator.generateOrderNumber());
        orderDTO.setCreatedDate(ZonedDateTime.now());
        orderDTO.setStatus(OrderStatus.NEW);
        orderDTO.setUserId(userId);

        List<CartDTO> cartDTOList = getAllCarts(userId);

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        for (CartDTO cartDTO : cartDTOList) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();

            orderItemDTO.setOrderNumber(orderDTO.getNumber());
            orderItemDTO.setProductId(cartDTO.getProductId());

            orderItemDTOList.add(orderItemDTO);

            price = price.add(cartDTO.getProductPrice()).multiply(BigDecimal.valueOf(cartDTO.getCount()));
            count += cartDTO.getCount();
        }

        orderDTO.setShippingAddress(shippingAddress);
        orderDTO.setPaymentType(paymentType);
        orderDTO.setCount(count);
        orderDTO.setPrice(price);
        orderDTO.setOrderItemDTOList(orderItemDTOList);

        orderRepository.save(orderMapper.toEntity(orderDTO));

        deleteCart(userId);

        return orderDTO;
    }

    public void deleteCart(String userId) {
        cartRepository.deleteAllByUserId(userId);
    }
}