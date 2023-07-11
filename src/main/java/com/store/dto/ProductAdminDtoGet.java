package com.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.entity.Product;
import com.store.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdminDtoGet {
    @JsonIgnore
    private Long id;
    private int article;
    private String name;
    private BigDecimal price;
    private String category;
    private String description;
    private int quantity;
    private ProductStatus productStatus;
    private String imagePath;
    public ProductAdminDtoGet fromDto(Product product){
        return new ProductAdminDtoGet()
                .setArticle(product.getArticle())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setCategory(product.getCategory())
                .setDescription(product.getDescription())
                .setQuantity(product.getQuantity())
                .setProductStatus(product.getProductStatus())
                .setImagePath(product.getImagePath());
    }
}
