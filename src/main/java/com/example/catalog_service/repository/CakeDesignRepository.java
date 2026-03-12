package com.example.catalog_service.repository;

import com.example.catalog_service.entity.CakeDesign;
import com.example.catalog_service.enums.DesignCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CakeDesignRepository extends JpaRepository<CakeDesign, Integer> {

    List<CakeDesign> findByIsActiveTrue();

    List<CakeDesign> findByIsActiveTrueAndDesignCategory(DesignCategory designCategory);

    Optional<CakeDesign> findByCode(Integer code);

    boolean existsByCode(Integer code);
}