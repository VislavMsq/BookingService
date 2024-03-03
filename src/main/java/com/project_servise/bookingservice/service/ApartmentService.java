package com.project_servise.bookingservice.service;

import com.project_servise.bookingservice.dto.ApartmentDTO;
import com.project_servise.bookingservice.dto.CreateApartmentDTO;
import com.project_servise.bookingservice.entity.Apartment;

import java.util.List;

public interface ApartmentService {
    ApartmentDTO createApartment(CreateApartmentDTO apartmentDTO);

    ApartmentDTO findApartment(String uuid);

    List<ApartmentDTO> findAllApartments();
}
