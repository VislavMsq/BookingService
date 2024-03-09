package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.ApartmentDTO;
import com.project_service.bookingservice.entity.Apartment;
import com.project_service.bookingservice.entity.ApartmentCategory;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.exception.ApartmentNotFoundException;
import com.project_service.bookingservice.mapper.ApartmentMapper;
import com.project_service.bookingservice.repository.ApartmentRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.ApartmentCategoryService;
import com.project_service.bookingservice.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;
    private final UserProvider userProvider;
    private final ApartmentCategoryService apartmentCategoryService;

    @Override
    @Transactional
    public ApartmentDTO createApartment(ApartmentDTO apartmentDTO) {
        User owner = userProvider.getCurrentUser();
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        apartment.setOwner(owner);
        return apartmentMapper.toDTO(apartmentRepository.save(apartment));
    }

    public Apartment find(String uuid) {
        return apartmentRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment not found!"));

    }

    @Override
    @Transactional
    public ApartmentDTO findApartment(String uuid) {
        return apartmentMapper.toDTO(find(uuid));
    }

    @Override
    @Transactional
    public List<ApartmentDTO> findAllApartments() {
        User user = userProvider.getCurrentUser();
        UUID id = user.getOwner() == null ? user.getId() : user.getOwner().getId();
        List<Apartment> apartments = apartmentRepository.findByOwnerId(id);
        return apartmentMapper.listToDTO(apartments);
    }

    @Override
    @Transactional
    public List<ApartmentDTO> findApartmentByCountry(String country) {
        User user = userProvider.getCurrentUser();
        UUID id = user.getOwner() == null ? user.getId() : user.getOwner().getId();
        List<Apartment> apartments = apartmentRepository.findByCountry(country, id);
        return apartmentMapper.listToDTO(apartments);
    }


    @Override
    @Transactional
    public List<ApartmentDTO> findApartmentByCity(String city) {
        User user = userProvider.getCurrentUser();
        UUID id = user.getOwner() == null ? user.getId() : user.getOwner().getId();
        List<Apartment> apartments = apartmentRepository.findByCity(city, id);
        return apartmentMapper.listToDTO(apartments);
    }

    @Override
    @Transactional
    public void setApartmentCategoryToApartments(List<String> apartmentIds, String apartmentCategoryId) {
        List<UUID> uuids = apartmentIds.stream()
                .map(UUID::fromString)
                .toList();
        List<Apartment> apartments = apartmentRepository.findAllById(uuids);
        ApartmentCategory apartmentCategory = apartmentCategoryService.getApartmentCategory(apartmentCategoryId);
        for (Apartment apartment : apartments) {
            apartment.setApartmentCategory(apartmentCategory);
        }
        apartmentRepository.saveAll(apartments);
    }

}
