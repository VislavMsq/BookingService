package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.ClientDto;
import com.project_service.bookingservice.entity.Client;
import com.project_service.bookingservice.exception.ClientNotFoundException;
import com.project_service.bookingservice.mapper.ClientMapper;
import com.project_service.bookingservice.repository.ClientRepository;
import com.project_service.bookingservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientDto findByPhone(String phone) {
        return clientMapper.mapToDto(clientRepository.findByPhone(phone)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with phone %s not found", phone))));
    }

    @Override
    @Transactional
    public Client createClient(ClientDto clientDto) {
        //todo getOwner from security
        Client client = clientMapper.mapToEntity(clientDto);
        clientRepository.save(client);
        return client;
    }

    @Override
    @Transactional
    public ClientDto findById(String id) {
        Client client = clientRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with id %s not found", id)));
        return clientMapper.mapToDto(client);
    }
}
