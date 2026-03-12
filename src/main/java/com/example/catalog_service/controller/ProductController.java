package com.example.catalog_service.controller;

import com.example.catalog_service.dto.ProductRequestDto;
import com.example.catalog_service.dto.ProductResponseDto;
import com.example.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> getAllActive() {
        return productService.getAllActive();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @GetMapping("/category/{code}")
    public List<ProductResponseDto> getByCategory(@PathVariable String code) {
        return productService.getByCategoryCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Integer id,
                                     @RequestBody ProductRequestDto dto) {
        return productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
    @GetMapping("/page")
    public Page<ProductResponseDto> getPaged(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort
    ) {
        return productService.getPaged(category, minPrice, maxPrice, page, size, sort);
    }

    @GetMapping("/search")
    public Page<ProductResponseDto> search(
            @RequestParam(name = "q") String q,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size,
            @RequestParam(name = "sort", defaultValue = "id,asc") String sort
    ) {
        return productService.search(q, category, minPrice, maxPrice, page, size, sort);
    }

}
