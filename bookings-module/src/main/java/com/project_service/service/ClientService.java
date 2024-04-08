package com.project_service.service;

import com.project_service.dto.ClientDto;

public interface ClientService {
    ClientDto findByPhone(String phone);

    ClientDto create(ClientDto clientDto);

    ClientDto findById(String id);
}
