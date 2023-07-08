package com.store.service;

import com.store.dto.ProductAdminDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductAdminService {

    ProductAdminDto addProduct(ProductAdminDto productAdminDto) throws IOException;
    ProductAdminDto saveImage(Long productId, MultipartFile imageFile) throws IOException;
    List<ProductAdminDto> getActiveAndTemporarilyAbsentProducts();
}
