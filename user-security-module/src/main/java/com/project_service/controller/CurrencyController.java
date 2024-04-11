package com.project_service.controller;

import com.project_service.dto.CurrencyDto;
import com.project_service.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public List<CurrencyDto> getAllCurrency() {
        return currencyService.getAll();
    }
}
