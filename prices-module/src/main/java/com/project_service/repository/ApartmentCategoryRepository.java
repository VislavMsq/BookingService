package com.project_service.repository;

import com.project_service.entity.ApartmentCategory;
import com.project_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApartmentCategoryRepository extends JpaRepository<ApartmentCategory, UUID> {

    List<ApartmentCategory> findByOwner(User owner);
}
