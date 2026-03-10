package com.example.catalog_service.dto;

import com.example.catalog_service.enums.DesignCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Boolean isActive;
    private String categoryCode;
    private String categoryName;
    private DesignCategory designCategory;
}
