package com.project_service.mapper;

import com.project_service.dto.ApartmentDto;
import com.project_service.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {

    @Mapping(source = "apartmentCategory.id", target = "apartmentCategoryId")
    @Mapping(source = "parent.id", target = "parentId")
    ApartmentDto toDTO(Apartment apartment);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" , expression = "java(false)")
    Apartment toEntity(ApartmentDto apartmentDTO);

    List<ApartmentDto> listToDTO(List<Apartment> apartments);
}
