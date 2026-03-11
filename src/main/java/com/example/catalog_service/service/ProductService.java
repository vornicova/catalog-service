package com.example.catalog_service.service;

import com.example.catalog_service.dto.ProductRequestDto;
import com.example.catalog_service.dto.ProductResponseDto;
import com.example.catalog_service.enums.DesignCategory;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductResponseDto> getAllActive();

    List<ProductResponseDto> getByCategoryCode(String categoryCode);

    List<ProductResponseDto> getByCategoryCodeAndDesignCategory(String categoryCode, DesignCategory designCategory);


    ProductResponseDto getById(Integer id);

    ProductResponseDto create(ProductRequestDto dto);

    ProductResponseDto update(Integer id, ProductRequestDto dto);

    ProductResponseDto updateStatus(Integer id, Boolean isActive);

    Page<ProductResponseDto> getPaged(String categoryCode,
                                      DesignCategory designCategory,
                                      BigDecimal minPrice,
                                      BigDecimal maxPrice,
                                      int page,
                                      int size,
                                      String sort);

    void delete(Integer id);

    List<ProductResponseDto> getCakesCatalog();

    Page<ProductResponseDto> search(String text,
                                    String categoryCode,
                                    DesignCategory designCategory,
                                    BigDecimal minPrice,
                                    BigDecimal maxPrice,
                                    int page,
                                    int size,
                                    String sort);

}
