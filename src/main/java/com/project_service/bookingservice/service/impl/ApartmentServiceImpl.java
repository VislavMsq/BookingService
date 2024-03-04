package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.ApartmentDTO;
import com.project_service.bookingservice.dto.CreateApartmentDTO;
import com.project_service.bookingservice.entity.Apartment;
import com.project_service.bookingservice.exception.ApartmentNotFoundException;
import com.project_service.bookingservice.mapper.ApartmentMapper;
import com.project_service.bookingservice.repository.ApartmentRepository;
import com.project_service.bookingservice.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;

    @Override
    public ApartmentDTO createApartment(CreateApartmentDTO apartmentDTO) {
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        return apartmentMapper.toDTO(apartmentRepository.save(apartment));
    }

    public Apartment find(String uuid) {
        return apartmentRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment not found!"));

    }

    @Override
    public ApartmentDTO findApartment(String uuid){
        return apartmentMapper.toDTO(find(uuid));
    }

    @Override
    public List<ApartmentDTO> findAllApartments(){
        List<Apartment> apartments = apartmentRepository.findAll();
        return apartmentMapper.listToDTO(apartments);
    }

}
