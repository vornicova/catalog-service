package com.example.catalog_service.controller;

import com.example.catalog_service.dto.CakeDesignResponseDto;
import com.example.catalog_service.enums.DesignCategory;
import com.example.catalog_service.service.CakeDesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cake-designs")
@RequiredArgsConstructor
public class CakeDesignController {

    private final CakeDesignService cakeDesignService;

    @GetMapping
    public List<CakeDesignResponseDto> getAll(
            @RequestParam(name = "category", required = false) DesignCategory category
    ) {
        if (category != null) {
            return cakeDesignService.getByCategory(category);
        }
        return cakeDesignService.getAllActive();
    }

    @GetMapping("/{id}")
    public CakeDesignResponseDto getById(@PathVariable Integer id) {
        return cakeDesignService.getById(id);
    }
}