package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.CurrencyDto;
import com.project_service.bookingservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/all")
    private List<CurrencyDto> getAllCurrency() {
        return currencyService.getAll();
    }
}
