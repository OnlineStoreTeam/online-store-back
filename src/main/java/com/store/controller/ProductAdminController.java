package com.store.controller;

import com.store.dto.ProductAdminDto;
import com.store.entity.Product;
import com.store.service.impl.ProductAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @version 0.0.1
 */


@RestController
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final ProductAdminServiceImpl productAdminService;

    @Autowired
    public ProductAdminController(ProductAdminServiceImpl productAdminService) {
        this.productAdminService = productAdminService;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductAdminDto productAdminDto) {
        Product product = productAdminService.addProduct(productAdminDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
}
