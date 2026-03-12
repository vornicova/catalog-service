package com.example.catalog_service.service.impl;

import com.example.catalog_service.dto.CakeDesignRequestDto;
import com.example.catalog_service.dto.CakeDesignResponseDto;
import com.example.catalog_service.entity.CakeDesign;
import com.example.catalog_service.enums.DesignCategory;
import com.example.catalog_service.exception.NotFoundException;
import com.example.catalog_service.mapper.CakeDesignMapper;
import com.example.catalog_service.repository.CakeDesignRepository;
import com.example.catalog_service.service.CakeDesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CakeDesignServiceImpl implements CakeDesignService {

    private final CakeDesignRepository cakeDesignRepository;
    private final CakeDesignMapper cakeDesignMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CakeDesignResponseDto> getAllActive() {
        return cakeDesignMapper.toDtoList(cakeDesignRepository.findByIsActiveTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CakeDesignResponseDto> getByCategory(DesignCategory designCategory) {
        return cakeDesignMapper.toDtoList(
                cakeDesignRepository.findByIsActiveTrueAndDesignCategory(designCategory)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CakeDesignResponseDto getById(Integer id) {
        CakeDesign cakeDesign = cakeDesignRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cake design not found: " + id));
        return cakeDesignMapper.toDto(cakeDesign);
    }

    @Override
    public CakeDesignResponseDto create(CakeDesignRequestDto dto) {
        if (cakeDesignRepository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Cake design code already exists: " + dto.getCode());
        }

        CakeDesign cakeDesign = CakeDesign.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .designCategory(dto.getDesignCategory())
                .decorPrice(dto.getDecorPrice())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();

        return cakeDesignMapper.toDto(cakeDesignRepository.save(cakeDesign));
    }

    @Override
    public CakeDesignResponseDto update(Integer id, CakeDesignRequestDto dto) {
        CakeDesign cakeDesign = cakeDesignRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cake design not found: " + id));

        if (dto.getCode() != null) {
            cakeDesign.setCode(dto.getCode());
        }
        if (dto.getName() != null) {
            cakeDesign.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            cakeDesign.setDescription(dto.getDescription());
        }
        if (dto.getImageUrl() != null) {
            cakeDesign.setImageUrl(dto.getImageUrl());
        }
        if (dto.getDesignCategory() != null) {
            cakeDesign.setDesignCategory(dto.getDesignCategory());
        }
        if (dto.getDecorPrice() != null) {
            cakeDesign.setDecorPrice(dto.getDecorPrice());
        }
        if (dto.getIsActive() != null) {
            cakeDesign.setIsActive(dto.getIsActive());
        }

        return cakeDesignMapper.toDto(cakeDesignRepository.save(cakeDesign));
    }

    @Override
    public CakeDesignResponseDto updateStatus(Integer id, Boolean isActive) {
        CakeDesign cakeDesign = cakeDesignRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cake design not found: " + id));

        cakeDesign.setIsActive(isActive);

        return cakeDesignMapper.toDto(cakeDesignRepository.save(cakeDesign));
    }

    @Override
    public void delete(Integer id) {
        if (!cakeDesignRepository.existsById(id)) {
            throw new NotFoundException("Cake design not found: " + id);
        }
        cakeDesignRepository.deleteById(id);
    }
}