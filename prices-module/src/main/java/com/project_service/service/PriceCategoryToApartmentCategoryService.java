package com.project_service.service;

import com.project_service.dto.PriceCategoryToApartmentCategoryDto;

public interface PriceCategoryToApartmentCategoryService {

    PriceCategoryToApartmentCategoryDto create(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto);

    PriceCategoryToApartmentCategoryDto getDto(String id);

    PriceCategoryToApartmentCategoryDto update(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto);
}