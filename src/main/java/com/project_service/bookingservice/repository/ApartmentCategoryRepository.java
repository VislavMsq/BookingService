package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.ApartmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApartmentCategoryRepository extends JpaRepository<ApartmentCategory, UUID> {
}
