package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.ApartmentDto;

import java.util.List;

public interface ApartmentService {
    ApartmentDto createApartment(ApartmentDto apartmentDTO);

    ApartmentDto findApartment(String uuid);

    List<ApartmentDto> findAllApartments();

    List<ApartmentDto> findApartmentByCountry(String country);

    List<ApartmentDto> findApartmentByCity(String city);

    void setApartmentCategoryToApartments(List<String> apartments, String apartmentCategoryId);
}
