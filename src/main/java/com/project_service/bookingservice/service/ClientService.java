package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.ClientDto;

public interface ClientService {
    ClientDto findByPhone(String phone);

    ClientDto create(ClientDto clientDto);

    ClientDto findById(String id);
}
