package com.project_servise.bookingservice.service;

import com.project_servise.bookingservice.dto.ClientDto;
import com.project_servise.bookingservice.entity.Client;

public interface ClientService {
    ClientDto findByPhone(String phone);

    Client create(ClientDto clientDto);

    ClientDto findById(String id);
}
