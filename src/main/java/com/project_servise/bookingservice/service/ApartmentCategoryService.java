package com.project_servise.bookingservice.service;

import com.project_servise.bookingservice.dto.ApartmentCategoryCreateDTO;

import java.util.List;

public interface ApartmentCategoryService {
    ApartmentCategoryCreateDTO createApartmentCategory(ApartmentCategoryCreateDTO apartmentCategoryCreateDTO);

    ApartmentCategoryCreateDTO getDTO(String uuid);

    List<ApartmentCategoryCreateDTO> getList();
}
