package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.ApartmentCategoryDto;
import com.project_service.bookingservice.entity.ApartmentCategory;

import java.util.List;

public interface ApartmentCategoryService {
    ApartmentCategoryDto createApartmentCategory(ApartmentCategoryDto apartmentCategoryCreateDTO);

    ApartmentCategory getApartmentCategory(String uuid);

    ApartmentCategoryDto getDTO(String uuid);

    List<ApartmentCategoryDto> getList();

    void deleteApartmentCategory(String id);
}
