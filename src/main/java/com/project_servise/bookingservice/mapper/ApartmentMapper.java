package com.project_servise.bookingservice.mapper;

import com.project_servise.bookingservice.dto.ApartmentDTO;
import com.project_servise.bookingservice.dto.CreateApartmentDTO;
import com.project_servise.bookingservice.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {

    @Mapping(source = "apartmentCategoryId.id",target = "apartmentCategoryId")
    ApartmentDTO toDTO(Apartment apartment);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "apartmentCategoryId",target = "apartmentCategoryId.id")
    @Mapping(source = "currency",target = "currency.id")
    @Mapping(source = "parent", target = "parent.id")
    Apartment toEntity(CreateApartmentDTO apartmentDTO);

    @Mapping(source = "apartmentCategoryId.id",target = "apartmentCategoryId")
    List<ApartmentDTO> listToDTO(List<Apartment> apartments);
}
