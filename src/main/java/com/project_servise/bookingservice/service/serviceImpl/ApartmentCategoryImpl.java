package com.project_servise.bookingservice.service.serviceImpl;

import com.project_servise.bookingservice.dto.ApartmentCategoryCreateDTO;
import com.project_servise.bookingservice.entity.ApartmentCategory;
import com.project_servise.bookingservice.exception.ApartmentCategoryNotFoundException;
import com.project_servise.bookingservice.mapper.ApartmentCategoryMapper;
import com.project_servise.bookingservice.repository.ApartmentCategoryRepository;
import com.project_servise.bookingservice.service.ApartmentCategoryService;
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
    public ApartmentCategoryCreateDTO createApartmentCategory(ApartmentCategoryCreateDTO apartmentCategoryCreateDTO){
        ApartmentCategory apartmentCategory = apartmentCategoryMapper.toEntity(apartmentCategoryCreateDTO);
        apartmentCategoryRepository.save(apartmentCategory);
        return apartmentCategoryMapper.toDTO(apartmentCategory);
    }

    public ApartmentCategory getApartmentCategory(String uuid){
        return apartmentCategoryRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new ApartmentCategoryNotFoundException("Category not found!"));
    }

    @Override
    public ApartmentCategoryCreateDTO getDTO(String uuid){
        return apartmentCategoryMapper.toDTO(getApartmentCategory(uuid));
    }

    @Override
    public List<ApartmentCategoryCreateDTO> getList(){
        List<ApartmentCategory> categories = apartmentCategoryRepository.findAll();
        return apartmentCategoryMapper.toListDTO(categories);
    }

}
