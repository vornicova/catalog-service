package com.example.catalog_service.service;

import com.example.catalog_service.dto.ProductRequestDto;
import com.example.catalog_service.dto.ProductResponseDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductResponseDto> getAllActive();

    List<ProductResponseDto> getByCategoryCode(String categoryCode);

    ProductResponseDto getById(Integer id);

    ProductResponseDto create(ProductRequestDto dto);

    ProductResponseDto update(Integer id, ProductRequestDto dto);


    Page<ProductResponseDto> getPaged(String categoryCode,
                                      BigDecimal minPrice,
                                      BigDecimal maxPrice,
                                      int page,
                                      int size,
                                      String sort);
    void delete(Integer id);
    Page<ProductResponseDto> search(String text,
                                    String categoryCode,
                                    BigDecimal minPrice,
                                    BigDecimal maxPrice,
                                    int page,
                                    int size,
                                    String sort);

}
