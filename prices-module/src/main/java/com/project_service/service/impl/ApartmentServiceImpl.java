package com.project_service.service.impl;


import com.project_service.dto.ApartmentDto;
import com.project_service.entity.Apartment;
import com.project_service.entity.ApartmentCategory;
import com.project_service.entity.User;
import com.project_service.exception.ApartmentNotFoundException;
import com.project_service.mapper.ApartmentMapper;
import com.project_service.repository.ApartmentRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.ApartmentCategoryService;
import com.project_service.service.ApartmentService;
import com.project_service.service.UtilsService;
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
    public ApartmentDto createApartment(ApartmentDto apartmentDTO) {
        User owner = userProvider.getCurrentUser();
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        if (apartmentDTO.getApartmentCategoryId() != null) {
            ApartmentCategory apartmentCategory = apartmentCategoryService.getApartmentCategory(apartmentDTO.getApartmentCategoryId());
            apartment.setApartmentCategory(apartmentCategory);
        }
        if (apartmentDTO.getParentId() != null) {
            Apartment parentId = find(apartmentDTO.getParentId());
            apartment.setParent(parentId);
        }
        apartment.setOwner(owner);
        return apartmentMapper.toDTO(apartmentRepository.save(apartment));
    }

    public Apartment find(String uuid) {
        return apartmentRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment not found!"));

    }

    @Override
    @Transactional
    public ApartmentDto findApartment(String uuid) {
        return apartmentMapper.toDTO(find(uuid));
    }

    @Override
    @Transactional
    public List<ApartmentDto> findAllApartments() {
        User user = userProvider.getCurrentUser();
        UUID id = user.getOwner() == null ? user.getId() : user.getOwner().getId();
        List<Apartment> apartments = apartmentRepository.findByOwnerId(id);
        return apartmentMapper.listToDTO(apartments);
    }

    @Override
    @Transactional
    public List<ApartmentDto> findApartmentByCountry(String country) {
        User user = userProvider.getCurrentUser();
        UUID id = user.getOwner() == null ? user.getId() : user.getOwner().getId();
        List<Apartment> apartments = apartmentRepository.findByCountry(country, id);
        return apartmentMapper.listToDTO(apartments);
    }


    @Override
    @Transactional
    public List<ApartmentDto> findApartmentByCity(String city) {
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

        ApartmentCategory apartmentCategory = apartmentCategoryService.getApartmentCategory(apartmentCategoryId);
        User owner = userProvider.getCurrentUser();
        UtilsService.checkOwner(apartmentCategory, owner);
        List<Apartment> apartments = apartmentRepository.findAllByIdAndOwner(uuids, owner);
        for (Apartment apartment : apartments) {
            apartment.setApartmentCategory(apartmentCategory);
        }
        apartmentRepository.saveAll(apartments);
    }

}
