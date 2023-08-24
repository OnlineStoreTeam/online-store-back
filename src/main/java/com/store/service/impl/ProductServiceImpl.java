package com.store.service.impl;

import com.store.dto.ProductAdminDto;
import com.store.entity.ProductStatus;
import com.store.repository.ProductAdminRepository;
import com.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductAdminRepository productAdminRepository;

    @Override
    public Page<ProductAdminDto> findAll(Pageable paging) {
        Page<ProductAdminDto>  products = productAdminRepository.findAll(paging).map(ProductAdminDto::fromEntity);
        List<ProductAdminDto> sortedProducts = products.stream()
                .sorted(Comparator.comparing(ProductAdminDto::getProductStatus))
                .collect(Collectors.toList());
        return new PageImpl<>(sortedProducts, paging, products.getTotalElements());
    }

    @Override
    public Page<ProductAdminDto> getAllByCategory(Pageable paging, String category) {
        Page<ProductAdminDto>  products = productAdminRepository.findProductsByCategoryContains(paging, category)
                .map(ProductAdminDto::fromEntity);
        List<ProductAdminDto> sortedProducts = products.stream()
                .filter(product -> !product.getProductStatus().equals(ProductStatus.DELETE))
                .sorted(Comparator.comparing(ProductAdminDto::getProductStatus))
                .collect(Collectors.toList());
        return new PageImpl<>(sortedProducts, paging, products.getTotalElements());
    }
}
