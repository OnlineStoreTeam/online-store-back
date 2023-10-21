package com.store.restcontroller;


import com.store.dto.categoryDTOs.CategoryCreateDTO;
import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.dto.categoryDTOs.CategoryUpdateDTO;
import com.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "id") Long categoryId) {
        return ResponseEntity
                .ok()
                .body(categoryService.getCategoryById(categoryId));
    }

    @GetMapping()
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(categoryService.getAllCategories(pageable));
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        return categoryService.createCategory(categoryCreateDTO);
    }

    @PutMapping
    public CategoryDTO createCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        return categoryService.updateCategory(categoryUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}