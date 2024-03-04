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
    @Query("select ac from ApartmentCategory ac where ac.owner.id = :ownerId")
    List<Apartment> findByOwnerId(@Param("ownerId") String ownerId);
}
