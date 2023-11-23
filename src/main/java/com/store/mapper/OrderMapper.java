package com.store.mapper;

import com.store.dto.orderDTOs.OrderDTO;
import com.store.dto.orderDTOs.OrderItemDTO;
import com.store.entity.Order;
import com.store.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Mapping(target = "orderItemDTOList", source = "orderItemList")
    OrderDTO toDto(Order order);

    @Mapping(source = "orderItemDTOList", target = "orderItemList")
    Order toEntity(OrderDTO orderDTO);

    List<OrderItemDTO> toOrderItemDTOList(List<OrderItem> orderItems);

    List<OrderItem> toOrderItemList(List<OrderItemDTO> orderItemDTOList);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "order.number", source = "orderNumber")
    OrderItem toOrderItem(OrderItemDTO orderItemDTO);

    @Mapping(target = "orderNumber", source = "order.number")
    @Mapping(target = "productId", source = "product.id")
    OrderItemDTO toOrderItemDTO(OrderItem orderItem);
}
