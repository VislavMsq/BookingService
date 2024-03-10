package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.CategoryPriceSchedule;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceScheduleMapper {

    ScheduleDto toScheduleDto(CategoryPriceSchedule categoryPriceSchedule);
    List<ScheduleDto> toScheduleDto(List<CategoryPriceSchedule> categoryPriceScheduleList);
}
