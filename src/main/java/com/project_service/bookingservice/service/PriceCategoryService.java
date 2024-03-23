package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.PriceCategoryDto;

import java.util.List;

public interface PriceCategoryService {
    PriceCategoryDto createPriceOfCategory(PriceCategoryDto priceCategoryDto);

    PriceCategoryDto findById(String id);

    PriceCategoryDto update(PriceCategoryDto priceCategoryDto);

    List<PriceCategoryDto> findAll();
}
