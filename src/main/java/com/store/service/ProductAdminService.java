package com.store.service;

import com.store.dto.ProductAdminDto;

import java.io.IOException;

public interface ProductAdminService {

    ProductAdminDto addProduct(ProductAdminDto productAdminDto) throws IOException;
}
