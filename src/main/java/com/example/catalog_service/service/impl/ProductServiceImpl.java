package com.example.catalog_service.service.impl;

import com.example.catalog_service.dto.ProductRequestDto;
import com.example.catalog_service.dto.ProductResponseDto;
import com.example.catalog_service.entity.Category;
import com.example.catalog_service.entity.Product;
import com.example.catalog_service.exception.NotFoundException;
import com.example.catalog_service.mapper.ProductMapper;
import com.example.catalog_service.repository.CategoryRepository;
import com.example.catalog_service.repository.ProductRepository;
import com.example.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private Integer generateNextId() {
        return productRepository.findTopByOrderByIdDesc()
                .map(p -> p.getId() + 1)
                .orElse(1); // если таблица пустая, начнем с 1
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllActive() {
        return productMapper.toDtoList(productRepository.findByIsActiveTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getByCategoryCode(String categoryCode) {
        return productMapper.toDtoList(
                productRepository.findByCategory_CodeAndIsActiveTrue(categoryCode)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        return productMapper.toDto(product);

    }
    @Override
    public void delete(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDto create(ProductRequestDto dto) {
        // находим категорию по коду
        Category category = categoryRepository.findByCode(dto.getCategoryCode())
                .orElseThrow(() -> new NotFoundException("Category not found: " + dto.getCategoryCode()));

        Product product = new Product();

        // так как id у нас INTEGER без автоинкремента — возьмём max(id)+1
        product.setId(generateNextId());

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        product.setCategory(category);
        // если в dto есть imageUrl — добавь:
        // product.setImageUrl(dto.getImageUrl());

        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    public ProductResponseDto update(Integer id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));

        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getIsActive() != null) {
            product.setIsActive(dto.getIsActive());
        }
        if (dto.getCategoryCode() != null) {
            Category category = categoryRepository.findByCode(dto.getCategoryCode())
                    .orElseThrow(() -> new NotFoundException("Category not found: " + dto.getCategoryCode()));
            product.setCategory(category);
        }

        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getPaged(String categoryCode,
                                             BigDecimal minPrice,
                                             BigDecimal maxPrice,
                                             int page,
                                             int size,
                                             String sort) {

        Sort sortObj = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);

        boolean hasCategory = categoryCode != null && !categoryCode.isBlank();
        boolean hasPriceRange = minPrice != null && maxPrice != null;

        Page<Product> productPage;

        if (hasCategory && hasPriceRange) {
            productPage = productRepository
                    .findByIsActiveTrueAndCategory_CodeAndPriceBetween(categoryCode, minPrice, maxPrice, pageable);
        } else if (hasCategory) {
            productPage = productRepository
                    .findByIsActiveTrueAndCategory_Code(categoryCode, pageable);
        } else if (hasPriceRange) {
            productPage = productRepository
                    .findByIsActiveTrueAndPriceBetween(minPrice, maxPrice, pageable);
        } else {
            productPage = productRepository
                    .findByIsActiveTrue(pageable);
        }

        var contentDto = productMapper.toDtoList(productPage.getContent());
        return new PageImpl<>(contentDto, pageable, productPage.getTotalElements());
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by("id").ascending();
        }

        String[] parts = sort.split(",");
        String property = parts[0];
        String direction = parts.length > 1 ? parts[1] : "asc";

        if ("desc".equalsIgnoreCase(direction)) {
            return Sort.by(property).descending();
        } else {
            return Sort.by(property).ascending();
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> search(String text,
                                           String categoryCode,
                                           BigDecimal minPrice,
                                           BigDecimal maxPrice,
                                           int page,
                                           int size,
                                           String sort) {

        Sort sortObj = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);

        Page<Product> productPage = productRepository.searchActive(
                text,
                categoryCode,
                minPrice,
                maxPrice,
                pageable
        );

        var contentDto = productMapper.toDtoList(productPage.getContent());
        return new PageImpl<>(contentDto, pageable, productPage.getTotalElements());
    }

}
