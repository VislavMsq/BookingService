package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.ClientDto;
import com.project_service.bookingservice.entity.Client;
import org.springframework.transaction.annotation.Transactional;

public interface ClientService {
    ClientDto findByPhone(String phone);

    @Transactional
    Client create(ClientDto clientDto);

    ClientDto findById(String id);
}
