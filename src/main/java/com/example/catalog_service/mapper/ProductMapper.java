package com.example.catalog_service.mapper;

import com.example.catalog_service.dto.ProductResponseDto;
import com.example.catalog_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "categoryCode", source = "category.code")
    @Mapping(target = "categoryName", source = "category.name")
    ProductResponseDto toDto(Product product);

    List<ProductResponseDto> toDtoList(List<Product> products);


}
