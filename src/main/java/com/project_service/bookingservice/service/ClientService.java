package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.ClientDto;
import com.project_service.bookingservice.entity.Client;

public interface ClientService {
    ClientDto findByPhone(String phone);

    Client create(ClientDto clientDto);

    ClientDto findById(String id);
}
