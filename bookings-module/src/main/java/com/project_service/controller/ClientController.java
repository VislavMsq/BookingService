package com.project_service.controller;

import com.project_service.dto.ClientDto;
import com.project_service.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/clients")
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/phones/{phone}")
    public ClientDto findByPhone(@PathVariable("phone") String phone) {
        return clientService.findByPhone(phone);
    }

    @GetMapping("/{id}")
    public ClientDto findById(@PathVariable("id") String id) {
        return clientService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@RequestBody @Valid ClientDto clientDto) {
        return clientService.create(clientDto);
    }
}
