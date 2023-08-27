package com.store.controller;

import com.store.dto.ProductAdminDto;
import com.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductCatalogController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Page<ProductAdminDto>> getAllProducts(@RequestParam int page, @RequestParam int size) {
        Page<ProductAdminDto> products = productService.findAllSort(PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<Page<ProductAdminDto>> getProductsCategory(@RequestParam int page, @RequestParam int size,
                                                                      @PathVariable String category) {
        Page<ProductAdminDto> products = productService.getAllByCategory(PageRequest.of(page, size), category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
