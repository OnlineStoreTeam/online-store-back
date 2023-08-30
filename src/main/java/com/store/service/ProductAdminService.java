package com.store.service;

import com.store.dto.ProductAdminDto;
import com.store.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductAdminService {

    ProductAdminDto addProduct(ProductAdminDto productAdminDto) throws IOException;

    Page<ProductAdminDto> getActiveAndTemporarilyAbsentProducts(Pageable paging);

    void deleteProduct(Long productId);

    ProductAdminDto updateProduct(Long productId, ProductAdminDto productAdminDto);
}
