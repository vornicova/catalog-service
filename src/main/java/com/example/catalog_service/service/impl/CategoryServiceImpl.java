package com.example.catalog_service.service.impl;

import com.example.catalog_service.dto.CategoryDto;
import com.example.catalog_service.dto.ProductResponseDto;
import com.example.catalog_service.entity.Category;
import com.example.catalog_service.entity.Product;
import com.example.catalog_service.exception.NotFoundException;
import com.example.catalog_service.mapper.CategoryMapper;
import com.example.catalog_service.mapper.ProductMapper;
import com.example.catalog_service.repository.CategoryRepository;
import com.example.catalog_service.repository.ProductRepository;
import com.example.catalog_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getByCode(String code) {
        Category category = categoryRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Category not found: " + code));
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByCategoryCode(String code) {
        // можно или через репозиторий продуктов, или через products из Category
        List<Product> products = productRepository.findByCategory_CodeAndIsActiveTrue(code);
        return productMapper.toDtoList(products);
    }
}
