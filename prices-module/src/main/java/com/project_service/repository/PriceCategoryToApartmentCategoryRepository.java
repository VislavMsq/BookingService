package com.project_service.repository;

import com.project_service.entity.PriceCategoryToApartmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PriceCategoryToApartmentCategoryRepository
        extends JpaRepository<PriceCategoryToApartmentCategory, UUID> {
}
