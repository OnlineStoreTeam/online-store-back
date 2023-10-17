package com.store.restcontroller;


import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "id") Long categoryId) {
        return ResponseEntity
                .ok()
                .body(categoryService.getCategoryById(categoryId));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity
                .ok()
                .body(categoryService.getAllCategories());
    }
}
