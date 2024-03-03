package com.project_servise.bookingservice.mapper;

import com.project_servise.bookingservice.dto.ApartmentCategoryCreateDTO;
import com.project_servise.bookingservice.entity.ApartmentCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentCategoryMapper {

    ApartmentCategoryCreateDTO toDTO(ApartmentCategory apartmentCategory);

    ApartmentCategory toEntity(ApartmentCategoryCreateDTO apartmentCategoryCreateDTO);

    List<ApartmentCategoryCreateDTO> toListDTO(List<ApartmentCategory> apartmentCategories);
}
