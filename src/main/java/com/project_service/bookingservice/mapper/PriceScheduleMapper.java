package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.PriceCategorySchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PriceScheduleMapper {

    @Mapping(target = "startDate", source = "priceCategorySchedule", qualifiedByName = "toStartMonthDay")
    @Mapping(target = "endDate", source = "priceCategorySchedule", qualifiedByName = "toEndMonthDay")
    ScheduleDto toScheduleDto(PriceCategorySchedule priceCategorySchedule);

    @Mapping(target = "priceCategory", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startMonth", source = "startDate", qualifiedByName = "toMonthNumber")
    @Mapping(target = "startDay", source = "startDate", qualifiedByName = "toDayNumber")
    @Mapping(target = "endMonth", source = "endDate", qualifiedByName = "toMonthNumber")
    @Mapping(target = "endDay", source = "endDate", qualifiedByName = "toDayNumber")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    PriceCategorySchedule toEntity(ScheduleDto scheduleDto);

    @Named("toScheduleDtoSet")
    Set<ScheduleDto> toScheduleDto(Iterable<PriceCategorySchedule> priceCategoryScheduleList);

    @Named("toScheduleEntitySet")
    Set<PriceCategorySchedule> toEntity(Set<ScheduleDto> scheduleDtoSet);

    @Named("toMonthNumber")
    default Integer toMonthNumber(String monthDay) {
        return MonthDay.parse(monthDay, DateTimeFormatter.ofPattern("M-d")).getMonthValue();
    }

    @Named("toDayNumber")
    default Integer toDayNumber(String monthDay) {
        return MonthDay.parse(monthDay, DateTimeFormatter.ofPattern("M-d")).getDayOfMonth();
    }

    @Named("toStartMonthDay")
    default String toStartMonthDay(PriceCategorySchedule priceCategorySchedule) {
        return String.format("%d-%d", priceCategorySchedule.getStartMonth(), priceCategorySchedule.getStartDay());
    }

    @Named("toEndMonthDay")
    default String toEndMonthDay(PriceCategorySchedule priceCategorySchedule) {
        return String.format("%d-%d", priceCategorySchedule.getEndMonth(), priceCategorySchedule.getEndDay());
    }
}
