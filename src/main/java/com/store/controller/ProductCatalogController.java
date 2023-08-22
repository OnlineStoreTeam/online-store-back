package com.store.controller;

import com.store.dto.ProductAdminDto;
import com.store.service.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductCatalogController {
    private final ProductCatalogService productCatalogService;

    @GetMapping()
    public ResponseEntity<Page<ProductAdminDto>> getAllProducts(@RequestParam int page, @RequestParam int size) {
        Page<ProductAdminDto> products = productCatalogService
                .getProductCatalog(PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
