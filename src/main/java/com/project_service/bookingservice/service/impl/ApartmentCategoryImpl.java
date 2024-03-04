package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.ApartmentCategoryCreateDTO;
import com.project_service.bookingservice.dto.ApartmentCategoryDTO;
import com.project_service.bookingservice.entity.ApartmentCategory;
import com.project_service.bookingservice.exception.ApartmentCategoryNotFoundException;
import com.project_service.bookingservice.mapper.ApartmentCategoryMapper;
import com.project_service.bookingservice.repository.ApartmentCategoryRepository;
import com.project_service.bookingservice.service.ApartmentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApartmentCategoryImpl implements ApartmentCategoryService {

    private final ApartmentCategoryMapper apartmentCategoryMapper;
    private final ApartmentCategoryRepository apartmentCategoryRepository;

    @Override
    public ApartmentCategoryDTO createApartmentCategory(ApartmentCategoryCreateDTO apartmentCategoryCreateDTO){
        ApartmentCategory apartmentCategory = new ApartmentCategory();
        apartmentCategory = apartmentCategoryMapper.toEntity(apartmentCategoryCreateDTO);
        apartmentCategoryRepository.save(apartmentCategory);
        return apartmentCategoryMapper.toApartmentDTO(apartmentCategory);
    }

    public ApartmentCategory getApartmentCategory(String uuid){
        return apartmentCategoryRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new ApartmentCategoryNotFoundException("Category not found!"));
    }

    @Override
    public ApartmentCategoryDTO getDTO(String uuid){
        return apartmentCategoryMapper.toApartmentDTO(getApartmentCategory(uuid));
    }

    @Override
    public List<ApartmentCategoryDTO> getList(){
        List<ApartmentCategory> categories = apartmentCategoryRepository.findAll();
        return apartmentCategoryMapper.toListDTO(categories);
    }

}
