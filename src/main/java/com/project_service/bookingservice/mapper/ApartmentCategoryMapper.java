package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.ApartmentCategoryDto;
import com.project_service.bookingservice.entity.ApartmentCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentCategoryMapper {

    ApartmentCategoryDto toApartmentDTO(ApartmentCategory apartmentCategory);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" , expression = "java(false)")
    ApartmentCategory toEntity(ApartmentCategoryDto apartmentCategoryCreateDTO);

    List<ApartmentCategoryDto> toListDTO(List<ApartmentCategory> apartmentCategories);
}
