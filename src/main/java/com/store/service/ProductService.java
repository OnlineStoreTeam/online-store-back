package com.store.service;

import com.store.dto.ProductAdminDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductAdminDto> findAllSort(Pageable paging);

    Page<ProductAdminDto> getAllByCategory(Pageable paging, String category);
}
