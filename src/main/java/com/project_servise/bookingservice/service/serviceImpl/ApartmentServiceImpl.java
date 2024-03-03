package com.project_servise.bookingservice.service.serviceImpl;

import com.project_servise.bookingservice.dto.ApartmentDTO;
import com.project_servise.bookingservice.dto.CreateApartmentDTO;
import com.project_servise.bookingservice.entity.Apartment;
import com.project_servise.bookingservice.exception.ApartmentNotFoundException;
import com.project_servise.bookingservice.mapper.ApartmentMapper;
import com.project_servise.bookingservice.repository.ApartmentRepository;
import com.project_servise.bookingservice.service.ApartmentService;
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
        Apartment apartment = new Apartment();
        apartment = apartmentMapper.toEntity(apartmentDTO);
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
