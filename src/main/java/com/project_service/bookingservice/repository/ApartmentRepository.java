package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.Apartment;
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
    List<Apartment> findByCountry(@Param("country") String country,@Param("ownerId") UUID id);

    @Query("SELECT a FROM Apartment a WHERE a.city = :city AND a.owner.id = :ownerId")
    List<Apartment> findByCity(@Param("city") String city,@Param("ownerId") UUID uuid);

    @Query("SELECT a FROM Apartment a WHERE a.id IN :apartmentIds")
    List<Apartment> findAllById(List<UUID> apartmentIds);
}

