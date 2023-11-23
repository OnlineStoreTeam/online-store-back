package com.store.mapper;

import com.store.dto.cartDTOs.CartDTO;
import com.store.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper
public interface CartMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "product.name", target = "productName")
    CartDTO toDto(Cart cart);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "product.name", target = "productName")
    List<CartDTO> toDto(List<Cart> cartList);

    @Mapping(source = "productId", target = "product.id")
    Cart toEntity(CartDTO cartDTO);
}