package com.project_service.service;

import com.project_service.dto.PriceCategoryDto;
import com.project_service.entity.PriceCategory;

import java.util.List;

public interface PriceCategoryService {
    PriceCategoryDto createPriceOfCategory(PriceCategoryDto priceCategoryDto);

    PriceCategoryDto findById(String id);

    PriceCategoryDto update(PriceCategoryDto priceCategoryDto);

    List<PriceCategoryDto> findAll();

    PriceCategory findPriceCategoryById(String id);
}
