package com.example.catalog_service.mapper;

import com.example.catalog_service.dto.CakeDesignResponseDto;
import com.example.catalog_service.entity.CakeDesign;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CakeDesignMapper {

    CakeDesignResponseDto toDto(CakeDesign cakeDesign);

    List<CakeDesignResponseDto> toDtoList(List<CakeDesign> cakeDesigns);
}