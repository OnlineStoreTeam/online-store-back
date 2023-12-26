package com.store.mapper;

import com.store.dto.FavouriteItemDTO;
import com.store.entity.FavouriteItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface FavouriteItemMapper {
    @Mapping(source = "product.id", target="productId")
    @Mapping(source = "product.name", target="productName")
    FavouriteItemDTO toDto(FavouriteItem favouriteItem);

    @Mapping(source = "productId", target = "product.id")
    FavouriteItem toEntity(FavouriteItemDTO favouriteItemDTO);

    @Mapping(source = "product.id", target="productId")
    @Mapping(source = "product.name", target="productName")
    List<FavouriteItemDTO> toDto(List<FavouriteItem> favouriteItemList);
}