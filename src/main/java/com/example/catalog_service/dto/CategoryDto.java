package com.example.catalog_service.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class CategoryDto {
    private UUID id;
    private String code;
    private String name;
    private String description;
}
