package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.ScheduleDto;

import java.util.List;

public interface PriceScheduleService {
    void createPriceOfCategory(List<PriceCategoryDto> priceScheduleDto);

    List<ScheduleDto> findAll();
}
