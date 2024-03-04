package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, UUID> {
}
