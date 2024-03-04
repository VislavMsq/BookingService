package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.ApartmentCategoryDTO;
import com.project_service.bookingservice.entity.ApartmentCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentCategoryMapper {

    ApartmentCategoryDTO toApartmentDTO(ApartmentCategory apartmentCategory);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    ApartmentCategory toEntity(ApartmentCategoryDTO apartmentCategoryCreateDTO);

    List<ApartmentCategoryDTO> toListDTO(List<ApartmentCategory> apartmentCategories);
}
