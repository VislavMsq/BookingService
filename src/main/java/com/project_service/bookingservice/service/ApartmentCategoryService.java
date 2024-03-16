package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.ApartmentCategoryDTO;
import com.project_service.bookingservice.entity.ApartmentCategory;

import java.util.List;

public interface ApartmentCategoryService {
    ApartmentCategoryDTO createApartmentCategory(ApartmentCategoryDTO apartmentCategoryCreateDTO);

    ApartmentCategory getApartmentCategory(String uuid);

    ApartmentCategoryDTO getDTO(String uuid);

    List<ApartmentCategoryDTO> getList();

    void deleteApartmentCategory(String id);
}
