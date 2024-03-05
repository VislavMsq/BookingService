package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.ApartmentDTO;
import com.project_service.bookingservice.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {

    @Mapping(source = "apartmentCategoryId.id", target = "apartmentCategoryId")
    ApartmentDTO toDTO(Apartment apartment);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "apartmentCategoryId.id", source = "apartmentCategoryId")
    Apartment toEntity(ApartmentDTO apartmentDTO);

    List<ApartmentDTO> listToDTO(List<Apartment> apartments);
}
