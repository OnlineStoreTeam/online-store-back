package com.store.controller;

import com.store.dto.ProductAdminDto;
import com.store.entity.Product;
import com.store.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @version 0.0.1
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    @PostMapping
    public Long addProduct(@RequestBody ProductAdminDto productAdminDto) {
        Product product = productAdminService.addProduct(productAdminDto);
        return product.getId();
    }
}
