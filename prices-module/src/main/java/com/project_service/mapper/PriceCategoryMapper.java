package com.project_service.mapper;

import com.project_service.dto.PriceCategoryDto;
import com.project_service.entity.PriceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = PriceScheduleMapper.class)
public interface PriceCategoryMapper {
    @Mapping(source = "currency.code", target = "currencyCode")
    @Mapping(source = "priceCategoryScheduleSet", target = "periods", qualifiedByName = "toScheduleDtoSet")
    PriceCategoryDto mapToDto(PriceCategory priceCategory);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" , expression = "java(false)")
    @Mapping(target = "priceCategoryScheduleSet", source = "periods", qualifiedByName = "toScheduleEntitySet")
    PriceCategory mapToEntity(PriceCategoryDto priceCategoryDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "priority", source = "priority", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", source = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "priceCategoryScheduleSet", source = "periods", qualifiedByName = "toScheduleEntitySet",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PriceCategoryDto priceCategoryDto, @MappingTarget PriceCategory priceCategory);

    List<PriceCategoryDto> mapToListDto(List<PriceCategory> priceCategories);
}
