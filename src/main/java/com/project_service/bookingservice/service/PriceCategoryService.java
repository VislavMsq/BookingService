package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.PriceCategoryDto;

public interface PriceCategoryService {
    PriceCategoryDto createPriceOfCategory(PriceCategoryDto priceCategoryDto);

    PriceCategoryDto findById(String id);
}
