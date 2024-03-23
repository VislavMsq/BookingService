package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.PriceCategorySchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PriceScheduleMapper {

    ScheduleDto toScheduleDto(PriceCategorySchedule priceCategorySchedule);

    @Named("toScheduleDtoList")
    Set<ScheduleDto> toScheduleDto(Iterable<PriceCategorySchedule> priceCategoryScheduleList);

    @Mapping(target = "priceCategory", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted", expression = "java(false)")
    PriceCategorySchedule toEntity(ScheduleDto scheduleDto);

    @Named("toScheduleEntityList")
    Set<PriceCategorySchedule> toEntity(Set<ScheduleDto> scheduleDtoSet);
}
