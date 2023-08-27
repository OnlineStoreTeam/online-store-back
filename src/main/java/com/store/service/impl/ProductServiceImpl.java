package com.store.service.impl;

import com.store.dto.ProductAdminDto;
import com.store.repository.ProductAdminRepository;
import com.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductAdminRepository productAdminRepository;

    @Override
    public Page<ProductAdminDto> findAllSort(Pageable paging) {
        return productAdminRepository.findAllSort(paging).map(ProductAdminDto::fromEntity);
    }

    @Override
    public Page<ProductAdminDto> getAllByCategory(Pageable paging, String category) {
        return productAdminRepository.findProductsByCategory(paging, category).map(ProductAdminDto::fromEntity);
    }
}
