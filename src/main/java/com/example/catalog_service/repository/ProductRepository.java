package com.example.catalog_service.repository;

import com.example.catalog_service.entity.Product;
import com.example.catalog_service.enums.DesignCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByIsActiveTrue();

    List<Product> findByCategory_CodeAndIsActiveTrue(String categoryCode);

    List<Product> findByCategory_CodeAndDesignCategoryAndIsActiveTrue(
            String categoryCode,
            DesignCategory designCategory
    );

    Page<Product> findByIsActiveTrue(Pageable pageable);

    Page<Product> findByIsActiveTrueAndCategory_Code(String categoryCode, Pageable pageable);

    Page<Product> findByIsActiveTrueAndPriceBetween(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    Page<Product> findByIsActiveTrueAndCategory_CodeAndPriceBetween(
            String categoryCode,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    Page<Product> findByIsActiveTrueAndDesignCategory(
            DesignCategory designCategory,
            Pageable pageable
    );

    Page<Product> findByIsActiveTrueAndCategory_CodeAndDesignCategory(
            String categoryCode,
            DesignCategory designCategory,
            Pageable pageable
    );

    Page<Product> findByIsActiveTrueAndDesignCategoryAndPriceBetween(
            DesignCategory designCategory,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    Page<Product> findByIsActiveTrueAndCategory_CodeAndDesignCategoryAndPriceBetween(
            String categoryCode,
            DesignCategory designCategory,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    List<Product> findByIsActiveTrueAndCategory_CodeAndDesignCategoryIsNotNull(String categoryCode);

    Page<Product> findByIsActiveTrueAndCategory_CodeAndDesignCategoryIsNotNull(
            String categoryCode,
            Pageable pageable
    );

    Optional<Product> findTopByOrderByIdDesc();

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.isActive = true
              AND (:categoryCode IS NULL OR p.category.code = :categoryCode)
              AND (:designCategory IS NULL OR p.designCategory = :designCategory)
              AND (:minPrice IS NULL OR p.price >= :minPrice)
              AND (:maxPrice IS NULL OR p.price <= :maxPrice)
              AND (
                    :text IS NULL
                    OR :text = ''
                    OR LOWER(p.name) LIKE LOWER(CONCAT('%', :text, '%'))
                    OR LOWER(p.description) LIKE LOWER(CONCAT('%', :text, '%'))
                  )
            """)
    Page<Product> searchActive(
            @Param("text") String text,
            @Param("categoryCode") String categoryCode,
            @Param("designCategory") DesignCategory designCategory,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );
}