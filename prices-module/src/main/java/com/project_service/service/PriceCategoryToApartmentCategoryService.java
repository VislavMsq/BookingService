package com.project_service.service;

import com.project_service.dto.PriceCategoryToApartmentCategoryDto;
import com.project_service.entity.PriceCategory;
import com.project_service.entity.PriceCategoryToApartmentCategory;
import com.project_service.exception.PriceCategoryNotFoundException;

public interface PriceCategoryToApartmentCategoryService {

    PriceCategoryToApartmentCategoryDto create(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto);

    PriceCategoryToApartmentCategoryDto getDto(String id);

    PriceCategoryToApartmentCategoryDto update(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto);

    PriceCategory findPriceCategoryById(String id) throws PriceCategoryNotFoundException;
    PriceCategoryToApartmentCategory findPriceCategoryToApartmentCategoryById(String id) throws PriceCategoryNotFoundException;
}
