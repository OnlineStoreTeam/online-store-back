package com.store.controller;

import com.store.dto.ProductAdminDto;
import com.store.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/page")
    public ResponseEntity<List<ProductAdminDto>> getAllProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        List<ProductAdminDto> products = productAdminService.getActiveAndTemporarilyAbsentProducts();

        int totalElements = products.size();
        int start = page * size;
        int end = Math.min(start + size, totalElements);

        List<ProductAdminDto> paginatedProducts = products.subList(start, end);

        return new ResponseEntity<>(paginatedProducts, HttpStatus.OK);
    }
}
