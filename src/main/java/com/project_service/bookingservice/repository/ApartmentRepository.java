package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.Apartment;
import com.project_service.bookingservice.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, UUID> {
    @Query("select ac from Apartment ac where ac.owner.id = :ownerId")
    List<Apartment> findByOwnerId(@Param("ownerId") UUID ownerId);

    @Query("SELECT a FROM Apartment a WHERE a.country = :country AND a.owner.id = :ownerId")
    List<Apartment> findByCountry(@Param("country") String country, @Param("ownerId") UUID id);

    @Query("SELECT a FROM Apartment a WHERE a.city = :city AND a.owner.id = :ownerId")
    List<Apartment> findByCity(@Param("city") String city, @Param("ownerId") UUID uuid);

    @Query("SELECT a FROM Apartment a WHERE a.id IN :apartmentIds and a.owner = :owner")
    List<Apartment> findAllByIdAndOwner(List<UUID> apartmentIds, @Param("owner") User owner);

    @EntityGraph(attributePaths = {
            "owner",
            "apartmentCategory.categoryToCategorySet.currency",
            "apartmentCategory.categoryToCategorySet.priceCategory.priceCategoryScheduleSet"
    })
    @Query("select a from Apartment a " +
            "inner join PriceCategoryToApartmentCategory pcac on pcac.apartmentCategory.id = a.apartmentCategory.id " +
            "inner join PriceCategorySchedule pcs on pcac.priceCategory.id = pcs.priceCategory.id " +
            "where a.id in :apartmentIds and a.owner = :owner and pcac.period = :year " +
            "and pcs.startMonth <= :startMonth and pcs.startDay <= :startDay " +
            "and pcs.endMonth >= :endMonth and pcs.endDay >= :endDay")
    List<Apartment> findAllByIdAndOwnerWithPrices(List<String> apartmentIds, User owner, Integer year,
                                                  Integer startMonth, Integer startDay,
                                                  Integer endMonth, Integer endDay);

    @EntityGraph(attributePaths = {
            "owner",
            "apartmentCategory.categoryToCategorySet.currency",
            "apartmentCategory.categoryToCategorySet.priceCategory.priceCategoryScheduleSet"
    })
    @Query("select a from Apartment a " +
            "inner join PriceCategoryToApartmentCategory pcac on pcac.apartmentCategory.id = a.apartmentCategory.id " +
            "inner join PriceCategorySchedule pcs on pcac.priceCategory.id = pcs.priceCategory.id " +
            "where a.owner = :owner and pcac.period = :year " +
            "and pcs.startMonth <= :startMonth and pcs.startDay <= :startDay " +
            "and pcs.endMonth >= :endMonth and pcs.endDay >= :endDay")
    List<Apartment> findAllByOwnerWithPrices(User owner, Integer year,
                                             Integer startMonth, Integer startDay,
                                             Integer endMonth, Integer endDay);
}
