package com.project_service.service.impl;

import com.project_service.dto.ClientDto;
import com.project_service.entity.Client;
import com.project_service.entity.User;
import com.project_service.exception.ClientNotFoundException;
import com.project_service.mapper.ClientMapper;
import com.project_service.repository.ClientRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.ClientService;
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
    public ClientDto create(ClientDto clientDto) {
        User owner = userProvider.getCurrentUser();
        Client client = clientMapper.mapToEntity(clientDto);
        client.setOwner(owner);
        clientRepository.save(client);
        return clientMapper.mapToDto(client);
    }

    @Override
    @Transactional
    public ClientDto findById(String id) {
        Client client = clientRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with id %s not found", id)));
        return clientMapper.mapToDto(client);
    }
}
