package com.store.service;

import com.store.dto.ProductAdminDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductAdminDto> getAllProducts(Pageable paging);

    Page<ProductAdminDto> getProductsCategory(Pageable paging, String category);
}
