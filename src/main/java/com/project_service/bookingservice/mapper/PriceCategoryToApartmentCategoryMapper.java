package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.PriceCategoryToApartmentCategoryDto;
import com.project_service.bookingservice.entity.PriceCategoryToApartmentCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Year;

@Mapper(componentModel = "spring")
public interface PriceCategoryToApartmentCategoryMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "period", source = "year", qualifiedByName = "toYear")
    PriceCategoryToApartmentCategory mapToEntity(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto);

    @Mapping(target = "year", source = "period", qualifiedByName = "toInt")
    @Mapping(target = "apartmentCategoryId", source = "apartmentCategory.id")
    @Mapping(target = "priceCategoryId", source = "priceCategory.id")
    @Mapping(target = "currencyCode", source = "currency.code")
    PriceCategoryToApartmentCategoryDto mapToDto(PriceCategoryToApartmentCategory priceCategoryToApartmentCategory);

    @Named("toInt")
    default Integer toIntPars(Year year) {
        return year.getValue();
    }

    @Named("toYear")
    default Year toYearPars(Integer year) {
        return Year.of(year);
    }
}
