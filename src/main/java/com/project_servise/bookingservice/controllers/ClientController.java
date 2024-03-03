package com.project_servise.bookingservice.controllers;

import com.project_servise.bookingservice.dto.ClientDto;
import com.project_servise.bookingservice.service.ClientService;
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

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewClient(@RequestBody ClientDto clientDto) {
        return clientService.createClient(clientDto).getId().toString();
    }
}
