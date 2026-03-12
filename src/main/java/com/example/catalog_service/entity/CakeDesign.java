package com.example.catalog_service.entity;

import com.example.catalog_service.enums.DesignCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cake_designs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CakeDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "design_category", nullable = false)
    private DesignCategory designCategory;

    @Column(name = "decor_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal decorPrice;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}