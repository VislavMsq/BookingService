package com.project_service.repository;

import com.project_service.entity.PriceCategory;
import com.project_service.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceCategoryRepository extends JpaRepository<PriceCategory, UUID> {
    @EntityGraph("priceCategory.priceCategoryScheduleList")
    List<PriceCategory> findAllByOwner(User owner);

    @Override
    @NonNull
    @EntityGraph("priceCategory.priceCategoryScheduleList")
    Optional<PriceCategory> findById(@NonNull UUID id);
}
