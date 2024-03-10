package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.ClientDto;
import com.project_service.bookingservice.service.ClientService;
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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@RequestBody ClientDto clientDto) {
        return clientService.create(clientDto);
    }
}
