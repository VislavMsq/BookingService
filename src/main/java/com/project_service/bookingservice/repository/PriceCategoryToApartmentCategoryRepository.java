package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.ApartmentCategory;
import com.project_service.bookingservice.entity.PriceCategoryToApartmentCategory;
import com.project_service.bookingservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;
import java.util.UUID;

public interface PriceCategoryToApartmentCategoryRepository extends JpaRepository<PriceCategoryToApartmentCategory,
        UUID> {

    List<PriceCategoryToApartmentCategory> findByApartmentCategoryAndOwner(ApartmentCategory apartmentCategory,
                                                                           User owner);

    List<PriceCategoryToApartmentCategory> findAllByOwnerAndPeriod(User owner, Year year);
}
