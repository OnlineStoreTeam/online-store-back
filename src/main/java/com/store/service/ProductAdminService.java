package com.store.service;

import com.store.dto.ProductAdminDto;
import com.store.entity.Product;

public interface ProductAdminService {

    Product addProduct(ProductAdminDto productAdminDto);
}
