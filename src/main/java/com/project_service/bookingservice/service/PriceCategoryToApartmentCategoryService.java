package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.PriceCategoryToApartmentCategoryDto;

public interface PriceCategoryToApartmentCategoryService {

    PriceCategoryToApartmentCategoryDto create(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto);

    PriceCategoryToApartmentCategoryDto getDto(String id);

    PriceCategoryToApartmentCategoryDto update(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto);
}
