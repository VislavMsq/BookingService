package com.project_service.bookingservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PriceCategoryDto {
    private String id;
    private String currencyCode;
    private String name;
    private String priority;
    private Set<ScheduleDto> periods;
}
