package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.Client;
import com.project_service.bookingservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByPhoneAndOwner(String phone, User owner);
}
