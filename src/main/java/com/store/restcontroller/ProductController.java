package com.store.restcontroller;

import com.store.dto.productDTOs.ProductCreateDTO;
import com.store.dto.productDTOs.ProductDTO;
import com.store.dto.productDTOs.ProductUpdateDTO;
import com.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    private final ProductService productService;


    @GetMapping("/products")
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/product")
    public List<ProductDTO> getProductsCategory(Long categoryId, Pageable pageable) {
        return productService.getProductsByCategoryId(categoryId, pageable);
    }

    @PostMapping("/products")
    public ProductDTO addProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        return productService.addProduct(productCreateDTO);
    }

    @PutMapping("/products")
    public ProductDTO updateProduct(@RequestBody ProductUpdateDTO productUpdateDTO) {
        return productService.updateProduct(productUpdateDTO);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}