package com.store.controller;

import com.store.dto.ProductAdminDto;
import com.store.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @version 0.0.1
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody @Validated ProductAdminDto productAdminDto) throws IOException {
        ProductAdminDto productAdminDto1 = productAdminService.addProduct(productAdminDto);
        return new ResponseEntity<>(productAdminDto1.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/{productId}/image")
    public ResponseEntity<ProductAdminDto> saveImage(@RequestParam("imageFile") MultipartFile imageFile,
                                                     @PathVariable Long productId) throws IOException {
        ProductAdminDto productAdminDto1 = productAdminService.saveImage(productId, imageFile);
        return new ResponseEntity<>(productAdminDto1, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<Integer> getAllProducts() {
        List<ProductAdminDto> allProductAdminDto = productAdminService.getAllProducts();
        return new ResponseEntity<>(allProductAdminDto.size(), HttpStatus.OK);
    }

    @GetMapping("/products/page")
    public ResponseEntity<Page<ProductAdminDto>> getAllProductsPage(@RequestParam int page, @RequestParam int size) {
        Page<ProductAdminDto> allProductAdminDto = productAdminService.getAllProductsPage(PageRequest.of(page, size));
        return new ResponseEntity<>(allProductAdminDto, HttpStatus.OK);
    }
}
