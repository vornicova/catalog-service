package com.example.catalog_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;              // UUID, как в данных 0000-...

    @Column(name = "code", nullable = false, unique = true)
    private String code;          // CAKES, CUPCAKES, DESSERTS...

    @Column(name = "description")
    private String description;

    // ВАЖНО: поле мапим на колонку name, а не display_name
    @Column(name = "name", nullable = false)
    private String name;          // "Торты", "Капкейки" и т.п.

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;
}
