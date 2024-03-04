package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.ApartmentDTO;

import java.util.List;

public interface ApartmentService {
    ApartmentDTO createApartment(ApartmentDTO apartmentDTO);

    ApartmentDTO findApartment(String uuid);

    List<ApartmentDTO> findAllApartments();
}
