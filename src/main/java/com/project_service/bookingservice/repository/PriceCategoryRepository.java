package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.PriceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PriceCategoryRepository extends JpaRepository<PriceCategory, UUID> {


}
