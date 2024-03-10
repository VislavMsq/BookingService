package com.project_service.bookingservice.controller;


import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.service.PriceScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/price_schedules")
public class PriceScheduleController {
    private final PriceScheduleService priceScheduleService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPriceOfCategory(@RequestBody List<PriceCategoryDto> priceCategoryDtoList) {
        priceScheduleService.createPriceOfCategory(priceCategoryDtoList);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduleDto> findAll() {
        return priceScheduleService.findAll();
    }
}