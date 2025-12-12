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
import com.example.catalog_service.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getAll_shouldReturnMappedCategoryDtos() {
        // given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Category c1 = new Category();
        c1.setId(id1);
        Category c2 = new Category();
        c2.setId(id2);

        List<Category> entities = List.of(c1, c2);

        CategoryDto dto1 = new CategoryDto();
        dto1.setId(id1);
        CategoryDto dto2 = new CategoryDto();
        dto2.setId(id2);

        List<CategoryDto> dtos = List.of(dto1, dto2);

        when(categoryRepository.findAll()).thenReturn(entities);
        when(categoryMapper.toDtoList(entities)).thenReturn(dtos);

        // when
        List<CategoryDto> result = categoryService.getAll();

        // then
        verify(categoryRepository).findAll();
        verify(categoryMapper).toDtoList(entities);

        assertEquals(2, result.size());
        assertEquals(id1, result.get(0).getId());
        assertEquals(id2, result.get(1).getId());
    }

    @Test
    void getByCode_shouldReturnMappedDto_whenCategoryExists() {
        // given
        String code = "cakes";
        UUID id = UUID.randomUUID();

        Category category = new Category();
        category.setId(id);
        category.setCode(code);

        CategoryDto dto = new CategoryDto();
        dto.setId(id);
        dto.setCode(code);

        when(categoryRepository.findByCode(code)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(dto);

        // when
        CategoryDto result = categoryService.getByCode(code);

        // then
        verify(categoryRepository).findByCode(code);
        verify(categoryMapper).toDto(category);

        assertEquals(id, result.getId());
        assertEquals("cakes", result.getCode());
    }

    @Test
    void getByCode_shouldThrowNotFound_whenCategoryDoesNotExist() {
        String code = "unknown";

        when(categoryRepository.findByCode(code)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> categoryService.getByCode(code)
        );

        assertTrue(ex.getMessage().contains("Category not found: " + code));
        verify(categoryMapper, never()).toDto(any());
    }
}