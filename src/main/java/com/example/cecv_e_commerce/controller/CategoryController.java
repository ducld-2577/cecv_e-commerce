package com.example.cecv_e_commerce.controller;

import com.example.cecv_e_commerce.service.CategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cecv_e_commerce.domain.dto.category.CategoryListResponseDTO;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<CategoryListResponseDTO> getAllCategories() {
        CategoryListResponseDTO categories = categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }
}
