package com.project_servise.bookingservice.service.impl;

import com.project_servise.bookingservice.dto.ClientDto;
import com.project_servise.bookingservice.entity.Client;
import com.project_servise.bookingservice.entity.User;
import com.project_servise.bookingservice.exception.ClientNotFoundException;
import com.project_servise.bookingservice.mapper.ClientMapper;
import com.project_servise.bookingservice.repository.ClientRepository;
import com.project_servise.bookingservice.security.UserProvider;
import com.project_servise.bookingservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserProvider userProvider;

    @Override
    @Transactional
    public ClientDto findByPhone(String phone) {
        User owner = userProvider.getCurrentUser();
        return clientMapper.mapToDto(clientRepository.findByPhoneAndOwner(phone, owner)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with phone %s not found", phone))));
    }

    @Override
    @Transactional
    public Client create(ClientDto clientDto) {
        User owner = userProvider.getCurrentUser();
        Client client = clientMapper.mapToEntity(clientDto);
        client.setOwner(owner);
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
