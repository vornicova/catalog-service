package com.example.catalog_service.dto;

import com.example.catalog_service.enums.DesignCategory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CakeDesignResponseDto {
    private Integer id;
    private Integer code;
    private String name;
    private String description;
    private String imageUrl;
    private DesignCategory designCategory;
    private BigDecimal decorPrice;
    private Boolean isActive;
}