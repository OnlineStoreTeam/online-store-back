package com.store.service.impl;

import com.store.dto.ProductAdminDto;
import com.store.entity.Product;
import com.store.entity.ProductStatus;
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
        return productAdminRepository.save(
                new Product()
                        .setArticle(productAdminDto.getArticle())
                        .setName(productAdminDto.getName())
                        .setPrice(productAdminDto.getPrice())
                        .setCategory(productAdminDto.getCategory())
                        .setDescription(productAdminDto.getDescription())
                        .setQuantity(productAdminDto.getQuantity())
                        .setIsDeleted(ProductStatus.ACTIVE));
    }
}
