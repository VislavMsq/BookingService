package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.Apartment;
import com.project_service.bookingservice.entity.Price;
import com.project_service.bookingservice.entity.PriceCategory;
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

    @Query("select p from Price p where p.apartment.id = :uuid and p.date >= :startDate and p.date < :endDate")
    List<Price> findPricesOfApartments(@Param("uuid") UUID uuid, @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    @Query("select p from Price p where p.apartment.id = :apartmentId and p.date = :localDate")
    Optional<Price> findByApartmentAndDate(UUID apartmentId, LocalDate localDate);

    List<Price> findByApartment(Apartment apartment);

    @Query("select p from Price p join CategoryPriceSchedule cps on p.date between cps.startDate and cps.endDate " +
            "where cps.priceCategory = :priceCategory and p.apartment in :apartments")
    List<Price> findPricesOfApartmentsBetweenDates(List<Apartment> apartments, PriceCategory priceCategory);

    void deleteByApartmentIn(List<Apartment> apartments);


}
