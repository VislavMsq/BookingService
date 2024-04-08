package com.project_service.mapper;

import com.project_service.dto.ApartmentCategoryDto;
import com.project_service.entity.ApartmentCategory;
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
