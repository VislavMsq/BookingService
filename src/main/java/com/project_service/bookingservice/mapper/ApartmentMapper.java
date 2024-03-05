package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.ApartmentDTO;
import com.project_service.bookingservice.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {

    @Mapping(source = "apartmentCategoryId.id", target = "apartmentCategory")
    @Mapping(source = "parent.id", target = "parent")
    ApartmentDTO toDTO(Apartment apartment);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "apartmentCategoryId.id", source = "apartmentCategory")
    @Mapping(target = "parent.id", source = "parent")
    Apartment toEntity(ApartmentDTO apartmentDTO);

    @Mapping(source = "apartmentCategoryId.id", target = "apartmentCategory")
    @Mapping(source = "parent.id", target = "parent")
    List<ApartmentDTO> listToDTO(List<Apartment> apartments);
}
