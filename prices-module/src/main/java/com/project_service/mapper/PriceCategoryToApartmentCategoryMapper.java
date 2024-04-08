package com.project_service.mapper;

import com.project_service.dto.PriceCategoryToApartmentCategoryDto;
import com.project_service.entity.PriceCategoryToApartmentCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceCategoryToApartmentCategoryMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "period", source = "year")
    PriceCategoryToApartmentCategory mapToEntity(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto);

    @Mapping(target = "year", source = "period")
    @Mapping(target = "apartmentCategoryId", source = "apartmentCategory.id")
    @Mapping(target = "priceCategoryId", source = "priceCategory.id")
    @Mapping(target = "currencyCode", source = "currency.code")
    PriceCategoryToApartmentCategoryDto mapToDto(PriceCategoryToApartmentCategory priceCategoryToApartmentCategory);

}
