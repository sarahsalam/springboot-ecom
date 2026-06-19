package com.ecommerce.project.controller;

import com.ecommerce.project.dto.CategoryDTO;
import com.ecommerce.project.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create category - support /api/categories and /api/public/categories (2 paths)
    @PostMapping({"/categories", "/public/categories"})
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // Get all categories - support /api/categories and /api/public/categories (2 paths)
    @GetMapping({"/categories", "/public/categories"})
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Get single category by id - support /api/public/categories/{id} and /api/categories/{id} (2 paths)
    @GetMapping({"/public/categories/{id}", "/categories/{id}"})
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    // Update category (id from URL) - support /api/public/categories/{id} and /api/categories/{id} (2 paths)
    @PutMapping({"/public/categories/{id}", "/categories/{id}"})
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    // Delete category - support /api/admin/categories/{id} and /api/categories/{id} (2 paths)
    @DeleteMapping({"/admin/categories/{id}", "/categories/{id}"})
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
