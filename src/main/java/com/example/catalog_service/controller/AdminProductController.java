package com.example.catalog_service.controller;

import com.example.catalog_service.dto.ProductRequestDto;
import com.example.catalog_service.dto.ProductResponseDto;
import com.example.catalog_service.dto.ProductStatusUpdateDto;
import com.example.catalog_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor

public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto dto) {
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Integer id,
                                     @RequestBody ProductRequestDto dto) {
        return productService.update(id, dto);
    }

    @PatchMapping("/{id}/status")
    public ProductResponseDto updateStatus(@PathVariable Integer id,
                                           @RequestBody ProductStatusUpdateDto statusDto) {
        return productService.updateStatus(id, statusDto.getIsActive());
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
}
