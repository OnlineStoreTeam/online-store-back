package com.store.mapper;

import com.store.dto.orderDTOs.OrderDTO;
import com.store.dto.orderDTOs.OrderProductDTO;
import com.store.entity.Order;
import com.store.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Mapping(target = "orderProductDTOList", source = "orderProductList")
    OrderDTO toDto(Order order);

    @Mapping(target = "orderProductDTOList", source = "orderProductList")
    List<OrderDTO> toDto(List<Order> orderList);

    @Mapping(source = "orderProductDTOList", target = "orderProductList")
    Order toEntity(OrderDTO orderDTO);

    List<OrderProductDTO> toOrderProductDTOList(List<OrderProduct> orderProducts);

    List<OrderProduct> toOrderProductList(List<OrderProductDTO> orderProductDTOList);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "order.number", source = "orderNumber")
    OrderProduct toOrderItem(OrderProductDTO orderProductDTO);

    @Mapping(target = "orderNumber", source = "order.number")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target ="productName", source = "product.name")
    @Mapping(target = "productPrice", source = "product.price")
    OrderProductDTO toOrderItemDTO(OrderProduct orderProduct);
}