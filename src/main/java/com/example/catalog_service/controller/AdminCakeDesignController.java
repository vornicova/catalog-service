package com.example.catalog_service.controller;

import com.example.catalog_service.dto.CakeDesignRequestDto;
import com.example.catalog_service.dto.CakeDesignResponseDto;
import com.example.catalog_service.service.CakeDesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/cake-designs")
@RequiredArgsConstructor
public class AdminCakeDesignController {

    private final CakeDesignService cakeDesignService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CakeDesignResponseDto create(@RequestBody CakeDesignRequestDto dto) {
        return cakeDesignService.create(dto);
    }

    @PutMapping("/{id}")
    public CakeDesignResponseDto update(@PathVariable Integer id,
                                        @RequestBody CakeDesignRequestDto dto) {
        return cakeDesignService.update(id, dto);
    }

    @PatchMapping("/{id}/status")
    public CakeDesignResponseDto updateStatus(@PathVariable Integer id,
                                              @RequestParam Boolean isActive) {
        return cakeDesignService.updateStatus(id, isActive);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        cakeDesignService.delete(id);
    }
}