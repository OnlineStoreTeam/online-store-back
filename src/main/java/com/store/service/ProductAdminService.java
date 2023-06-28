package com.store.service;

import com.store.dto.ProductAdminDto;
import com.store.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductAdminService {

    ProductAdminDto addProduct(ProductAdminDto productAdminDto) throws IOException;

    ProductAdminDto saveImage(Long productId, MultipartFile imageFile) throws IOException;
    String uploadImage(MultipartFile imageFile) throws IOException;
}
