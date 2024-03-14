package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.entity.PriceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceCategoryMapper {
    @Mapping(source = "currency.code", target = "currencyCode")
    PriceCategoryDto mapToDto(PriceCategory priceCategory);

    List<PriceCategoryDto> mapToDto(List<PriceCategory> priceCategories);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    PriceCategory mapToEntity(PriceCategoryDto priceCategoryDto);

}
