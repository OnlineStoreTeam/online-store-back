package com.store.service.impl;

import com.store.dto.ProductAdminDto;
import com.store.entity.ProductStatus;
import com.store.repository.ProductRepository;
import com.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productAdminRepository;

    @Override
    public Page<ProductAdminDto> findAllSort(Pageable paging) {
        return productAdminRepository
                .findProductsByProductStatusIsNotOrderByProductStatus(ProductStatus.DELETE, paging)
                .map(ProductAdminDto::fromEntity);
    }

    @Override
    public Page<ProductAdminDto> getAllByCategory(Pageable paging, String category) {
        return productAdminRepository.findProductsByCategoryAndProductStatusIsNotOrderByProductStatus
                (category, ProductStatus.DELETE, paging).map(ProductAdminDto::fromEntity);
    }
}
