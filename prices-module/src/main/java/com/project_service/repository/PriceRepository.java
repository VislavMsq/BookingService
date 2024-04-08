package com.project_service.repository;

import com.project_service.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<Price, UUID> {

    @Query("select p from Price p where p.apartment.id = :uuid and p.date >= :startDate and p.date <= :endDate")
    List<Price> findPricesOfApartment(@Param("uuid") UUID uuid, @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    @Query("select p from Price p where p.apartment.id in :apartmentIds and p.date >= :startDate and p.date <= :endDate")
    List<Price> findPricesOfListApartments(List<String> apartmentIds, LocalDate startDate, LocalDate endDate);

    @Query("select p from Price p where p.apartment.id = :apartmentId and p.date = :localDate")
    Optional<Price> findByApartmentAndDate(UUID apartmentId, LocalDate localDate);
}
