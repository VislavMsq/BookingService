package com.project_service.service;

import com.project_service.dto.ApartmentCategoryDto;
import com.project_service.entity.ApartmentCategory;

import java.util.List;

public interface ApartmentCategoryService {
    ApartmentCategoryDto createApartmentCategory(ApartmentCategoryDto apartmentCategoryCreateDTO);

    ApartmentCategory getApartmentCategory(String uuid);

    ApartmentCategoryDto getDTO(String uuid);

    List<ApartmentCategoryDto> getList();

    void deleteApartmentCategory(String id);
}
