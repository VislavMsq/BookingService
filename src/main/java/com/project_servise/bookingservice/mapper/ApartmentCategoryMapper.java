package com.project_servise.bookingservice.mapper;

import com.project_servise.bookingservice.dto.ApartmentCategoryCreateDTO;
import com.project_servise.bookingservice.dto.ApartmentCategoryDTO;
import com.project_servise.bookingservice.entity.ApartmentCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentCategoryMapper {

    ApartmentCategoryDTO toApartmentDTO(ApartmentCategory apartmentCategory);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    ApartmentCategory toEntity(ApartmentCategoryCreateDTO apartmentCategoryCreateDTO);

    List<ApartmentCategoryDTO> toListDTO(List<ApartmentCategory> apartmentCategories);
}
