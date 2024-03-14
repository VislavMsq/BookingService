package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.PriceCategory;
import com.project_service.bookingservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PriceCategoryRepository extends JpaRepository<PriceCategory, UUID> {
    List<PriceCategory> findAllByOwner(User owner);
}
