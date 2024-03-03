package com.project_servise.bookingservice.service;

import com.project_servise.bookingservice.dto.ApartmentCategoryCreateDTO;
import com.project_servise.bookingservice.dto.ApartmentCategoryDTO;

import java.util.List;

public interface ApartmentCategoryService {
    ApartmentCategoryDTO createApartmentCategory(ApartmentCategoryCreateDTO apartmentCategoryCreateDTO);

    ApartmentCategoryDTO getDTO(String uuid);

    List<ApartmentCategoryDTO> getList();
}
