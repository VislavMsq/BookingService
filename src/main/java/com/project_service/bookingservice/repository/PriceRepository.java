package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<Price, UUID> {

    @Query("select p from Price p where p.apartment.id = :uuid and p.date >= :startDate and p.date < :endDate")
    List<Price> findPricesOfApartments(@Param("uuid") UUID uuid, @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);
}
