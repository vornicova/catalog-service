package com.example.catalog_service.service;

import com.example.catalog_service.dto.CakeDesignRequestDto;
import com.example.catalog_service.dto.CakeDesignResponseDto;
import com.example.catalog_service.enums.DesignCategory;

import java.util.List;

public interface CakeDesignService {

    List<CakeDesignResponseDto> getAllActive();

    List<CakeDesignResponseDto> getByCategory(DesignCategory designCategory);

    CakeDesignResponseDto getById(Integer id);

    CakeDesignResponseDto create(CakeDesignRequestDto dto);

    CakeDesignResponseDto update(Integer id, CakeDesignRequestDto dto);

    CakeDesignResponseDto updateStatus(Integer id, Boolean isActive);

    void delete(Integer id);
}