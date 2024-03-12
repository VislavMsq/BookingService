package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.CategoryPriceSchedule;
import com.project_service.bookingservice.entity.PriceCategory;
import com.project_service.bookingservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceScheduleMapper {

    ScheduleDto toScheduleDto(CategoryPriceSchedule categoryPriceSchedule);
    List<ScheduleDto> toScheduleDto(List<CategoryPriceSchedule> categoryPriceScheduleList);

    @Mapping(target = "priceCategory", expression = "java(priceCategory)")
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "owner", expression = "java(owner)")
    @Mapping(target = "startDate", expression = "java(priceScheduleDto.getStartDate().atStartOfDay())")
    @Mapping(target = "endDate", expression = "java(priceScheduleDto.getEndDate().atStartOfDay())")

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    CategoryPriceSchedule toEntity(PriceCategory priceCategory, User owner, ScheduleDto priceScheduleDto);
}
