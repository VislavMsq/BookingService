package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.ApartmentCategoryCreateDTO;
import com.project_service.bookingservice.dto.ApartmentCategoryDTO;

import java.util.List;

public interface ApartmentCategoryService {
    ApartmentCategoryDTO createApartmentCategory(ApartmentCategoryCreateDTO apartmentCategoryCreateDTO);

    ApartmentCategoryDTO getDTO(String uuid);

    List<ApartmentCategoryDTO> getList();
}
