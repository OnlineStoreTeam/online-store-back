package com.store.service;

import com.store.dto.ProductAdminDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCatalogService {

    Page<ProductAdminDto> getProductCatalog(Pageable paging);
}
