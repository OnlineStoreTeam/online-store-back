package com.store.restcontroller;


import com.store.constants.Role;
import com.store.dto.categoryDTOs.CategoryCreateDTO;
import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.dto.categoryDTOs.CategoryUpdateDTO;
import com.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@CrossOrigin(origins = "https://onlinestoreteam.github.io/products")
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

    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        return categoryService.createCategory(categoryCreateDTO);
    }

    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    @PutMapping
    public CategoryDTO createCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        return categoryService.updateCategory(categoryUpdateDTO);
    }

    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}