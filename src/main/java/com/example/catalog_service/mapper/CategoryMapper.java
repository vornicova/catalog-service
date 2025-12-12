package com.example.catalog_service.mapper;

import com.example.catalog_service.dto.CategoryDto;
import com.example.catalog_service.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    List<CategoryDto> toDtoList(List<Category> categories);
}
