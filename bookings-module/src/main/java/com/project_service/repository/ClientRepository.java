package com.project_service.repository;

import com.project_service.entity.Client;
import com.project_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByPhoneAndOwner(String phone, User owner);

}
