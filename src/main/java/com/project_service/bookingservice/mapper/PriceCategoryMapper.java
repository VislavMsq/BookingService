package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.entity.PriceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = PriceScheduleMapper.class)
public interface PriceCategoryMapper {
    @Mapping(source = "currency.code", target = "currencyCode")
    @Mapping(source = "priceCategoryScheduleList", target = "periods", qualifiedByName = "toScheduleDtoList")
    PriceCategoryDto mapToDto(PriceCategory priceCategory);

    List<PriceCategoryDto> mapToDto(List<PriceCategory> priceCategories);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" , expression = "java(false)")
    @Mapping(target = "priceCategoryScheduleList", source = "periods", qualifiedByName = "toScheduleEntityList")
    PriceCategory mapToEntity(PriceCategoryDto priceCategoryDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "priority", source = "priority", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", source = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "priceCategoryScheduleList", source = "periods", qualifiedByName = "toScheduleEntityList",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PriceCategoryDto priceCategoryDto, @MappingTarget PriceCategory priceCategory);
}
