package com.store.mapper;

import com.store.dto.productDTOs.ProductCreateDTO;
import com.store.dto.productDTOs.ProductDTO;
import com.store.dto.productDTOs.ProductUpdateDTO;
import com.store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductDTO toDto(Product product);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    List<ProductDTO> toDto(Page<Product> products);

    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDTO productDTO);

    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductCreateDTO productCreateDTO);

    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductUpdateDTO productUpdateDTO);
}
