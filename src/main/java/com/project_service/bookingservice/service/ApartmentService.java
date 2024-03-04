package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.ApartmentDTO;
import com.project_service.bookingservice.dto.CreateApartmentDTO;

import java.util.List;

public interface ApartmentService {
    ApartmentDTO createApartment(CreateApartmentDTO apartmentDTO);

    ApartmentDTO findApartment(String uuid);

    List<ApartmentDTO> findAllApartments();
}
