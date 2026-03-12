package com.example.catalog_service.dto;

import com.example.catalog_service.enums.DesignCategory;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CakeDesignRequestDto {
    private Integer code;
    private String name;
    private String description;
    private String imageUrl;
    private DesignCategory designCategory;
    private BigDecimal decorPrice;
    private Boolean isActive;
}