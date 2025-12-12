package com.example.catalog_service.service;

import com.example.catalog_service.dto.CategoryDto;
import com.example.catalog_service.dto.ProductResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll();

    CategoryDto getByCode(String code);

    List<ProductResponseDto> getProductsByCategoryCode(String code);
}
