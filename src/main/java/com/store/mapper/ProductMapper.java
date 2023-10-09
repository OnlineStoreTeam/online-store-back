package com.store.mapper;

import com.store.dto.productDTOs.ProductCreateDTO;
import com.store.dto.productDTOs.ProductDTO;
import com.store.dto.productDTOs.ProductUpdateDTO;
import com.store.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductDTO toDto(Product product);

    List<ProductDTO> toDto(Page<Product> products);

    Product toEntity(ProductDTO productDTO);

    Product toEntity(ProductCreateDTO productCreateDTO);

    Product toEntity(ProductUpdateDTO productUpdateDTO);
}
