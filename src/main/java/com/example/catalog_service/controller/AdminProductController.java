package com.example.catalog_service.controller;

import com.example.catalog_service.dto.ProductRequestDto;
import com.example.catalog_service.dto.ProductResponseDto;
import com.example.catalog_service.dto.ProductStatusUpdateDto;
import com.example.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
// если будет security:
// @PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;

    // Создать продукт
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return productService.create(dto);
    }

    // Обновить продукт целиком
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Integer id,
                                     @RequestBody ProductRequestDto dto) {
        return productService.update(id, dto);
    }

    // Мягкое выключение/включение (isActive)
    @PatchMapping("/{id}/status")
    public ProductResponseDto updateStatus(@PathVariable Integer id,
                                           @RequestBody ProductStatusUpdateDto statusDto) {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setIsActive(statusDto.getIsActive());
        // остальное не трогаем
        return productService.update(id, dto);
    }

    // Жёсткое удаление (если нужно)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
}
