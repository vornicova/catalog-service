package com.example.catalog_service.controller;

import com.example.catalog_service.dto.CategoryDto;
import com.example.catalog_service.dto.ProductResponseDto;
import com.example.catalog_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{code}")
    public CategoryDto getByCode(@PathVariable String code) {
        return categoryService.getByCode(code);
    }

    @GetMapping("/{code}/products")
    public List<ProductResponseDto> getProductsByCategory(@PathVariable String code) {
        return categoryService.getProductsByCategoryCode(code);
    }
}
