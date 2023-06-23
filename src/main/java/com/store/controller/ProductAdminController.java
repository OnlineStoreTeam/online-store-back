package com.store.controller;

import com.store.dto.ProductAdminDto;
import com.store.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProductAdminDto> addProduct(@RequestBody ProductAdminDto productAdminDto) {
        ProductAdminDto productAdminDto1 = productAdminService.addProduct(productAdminDto);
        return new ResponseEntity<>(productAdminDto1, HttpStatus.CREATED);
    }
}
