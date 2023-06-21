package com.store.service.impl;

import com.store.dto.ProductAdminDto;
import com.store.entity.Product;
import com.store.repository.ProductAdminRepository;
import com.store.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAdminServiceImpl implements ProductAdminService {

    private final ProductAdminRepository productAdminRepository;

    @Override
    public Product addProduct(ProductAdminDto productAdminDto) {
        Product product = new Product();
        product.setArticle(productAdminDto.getArticle());
        product.setName(productAdminDto.getName());
        product.setPrice(productAdminDto.getPrice());
        product.setCategory(productAdminDto.getCategory());
        product.setDescription(productAdminDto.getDescription());
        product.setQuantity(productAdminDto.getQuantity());
        return productAdminRepository.save(product);
    }
}
