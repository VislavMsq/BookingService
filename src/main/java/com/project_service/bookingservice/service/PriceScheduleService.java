package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.PriceCategory;

import java.util.List;

public interface PriceScheduleService {
    PriceCategoryDto createPriceSchedule(PriceCategory priceCategory, List<ScheduleDto> scheduleDtoList);
}
