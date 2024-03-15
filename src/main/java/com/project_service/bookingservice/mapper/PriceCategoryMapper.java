package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.entity.PriceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceCategoryMapper {
    @Mapping(source = "currency.code", target = "currencyCode")
    PriceCategoryDto mapToDto(PriceCategory priceCategory);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" , expression = "java(false)")
    PriceCategory mapToEntity(PriceCategoryDto priceCategoryDto);

}
