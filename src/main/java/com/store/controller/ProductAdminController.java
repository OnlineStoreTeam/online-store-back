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

import java.io.IOException;

/**
 * @version 0.0.1
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody @Validated ProductAdminDto product) throws IOException {
        ProductAdminDto productAdminDto = productAdminService.addProduct(product);
        return new ResponseEntity<>(productAdminDto.getId(), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductAdminDto>> getAllProducts(@RequestParam int page, @RequestParam int size) {
        Page<ProductAdminDto> products = productAdminService
                .getActiveAndTemporarilyAbsentProducts(PageRequest.of(page, size));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productAdminService.deleteProduct(productId);
        return new ResponseEntity<>("Product successfully removed.", HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductAdminDto> updateProduct(@PathVariable Long productId,
                                                         @RequestBody ProductAdminDto productAdminDto){
        ProductAdminDto productAdminDto1 = productAdminService.updateProduct(productId, productAdminDto);
        return new ResponseEntity<>(productAdminDto1, HttpStatus.OK);
    }
}
