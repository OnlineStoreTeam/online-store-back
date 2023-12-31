package com.store.service;

import com.store.dto.cartDTOs.CartDTO;
import com.store.dto.orderDTOs.OrderDTO;
import com.store.dto.orderDTOs.OrderProductDTO;
import com.store.enums.OrderStatus;
import com.store.exception.DataNotFoundException;
import com.store.exception.InvalidDataException;
import com.store.mapper.CartMapper;
import com.store.mapper.OrderMapper;
import com.store.repository.CartRepository;
import com.store.repository.OrderRepository;
import com.store.repository.ProductRepository;
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
    private final ProductRepository productRepository;


    public List<CartDTO> getAllCarts(String userId) {
        if (!cartRepository.existsAllByUserId(userId)) {
            throw new DataNotFoundException("There is no cart for current logged user");
        }
        return cartMapper.toDto(cartRepository.findAllByUserId(userId));
    }

    public CartDTO addProductToCart(Long productId, String userId) {
        if(!productRepository.existsById(productId)){
            throw new DataNotFoundException("There is no product with id " + productId);
        }
        if (cartRepository.existsByProductIdAndUserId(productId, userId)) {
            throw new InvalidDataException("Product with id " + productId + " is already in your cart");
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserId(userId);
        cartDTO.setProductId(productId);
        cartDTO.setCount(1);


        return cartMapper.toDto(cartRepository.save(cartMapper.toEntity(cartDTO)));
    }

    public CartDTO updateCountOfProduct(Long productId, Integer count, String userId) {
        if (!cartRepository.existsByProductIdAndUserId(productId, userId)) {
            throw new DataNotFoundException("There is no cart for user with id " + userId + " and products with id " + productId);
        }
        if (!(count > 1)) {
            throw new InvalidDataException("Count can't be less than 1, please check input data");
        }
        CartDTO cartDTO = cartMapper.toDto(cartRepository.findCartByProductIdAndUserId(productId, userId));

        cartDTO.setCount(count);

        return cartMapper.toDto(cartRepository.save(cartMapper.toEntity(cartDTO)));
    }

    public void deleteCartByProductId(Long productId, String userId) {
        if (!cartRepository.existsByProductIdAndUserId(productId, userId)) {
            throw new DataNotFoundException("There is no products in cart with id " + productId + "for current logged user");
        }
        cartRepository.deleteCartByProductId(productId);
    }


    public OrderDTO createOrderFromCart(String shippingAddress, String paymentType, String userId) {
        if (!cartRepository.existsAllByUserId(userId)) {
            throw new InvalidDataException("There is no cart for current logged user");
        }

        Integer count = 0;
        BigDecimal price = BigDecimal.ZERO;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNumber(OrderNumberGenerator.generateOrderNumber());
        orderDTO.setCreatedDate(ZonedDateTime.now());
        orderDTO.setStatus(OrderStatus.NEW);
        orderDTO.setUserId(userId);

        List<CartDTO> cartDTOList = getAllCarts(userId);

        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();
        for (CartDTO cartDTO : cartDTOList) {
            OrderProductDTO orderProductDTO = new OrderProductDTO();

            orderProductDTO.setOrderNumber(orderDTO.getNumber());
            orderProductDTO.setProductId(cartDTO.getProductId());
            if (cartDTO.getCount() < 1) {
                throw new InvalidDataException("Please check count of items in your cart");
            }
            orderProductDTOList.add(orderProductDTO);

            price = price.add(cartDTO.getProductPrice()).multiply(BigDecimal.valueOf(cartDTO.getCount()));
            count += cartDTO.getCount();
        }

        orderDTO.setShippingAddress(shippingAddress);
        orderDTO.setPaymentType(paymentType);
        orderDTO.setCount(count);
        orderDTO.setPrice(price);
        orderDTO.setOrderProductDTOList(orderProductDTOList);

        orderRepository.save(orderMapper.toEntity(orderDTO));

        deleteCart(userId);

        return orderDTO;
    }

    public void deleteCart(String userId) {
        if(!cartRepository.existsAllByUserId(userId)){
            throw new DataNotFoundException("There is no cart for current logged user");
        }

        cartRepository.deleteAllByUserId(userId);
    }
}