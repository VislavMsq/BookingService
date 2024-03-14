package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.ApartmentCategory;
import com.project_service.bookingservice.entity.PriceCategoryToApartmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PriceCategoryToApartmentCategoryRepository extends JpaRepository<PriceCategoryToApartmentCategory,
        UUID> {

    List<PriceCategoryToApartmentCategory> findByApartmentCategory(ApartmentCategory apartmentCategory);

}
