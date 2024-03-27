package com.project_service.bookingservice.dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.Set;

@Data
public class PriceCategoryDto {
    private String id;
    private String currencyCode;
    private String name;
    private String priority;
    @Valid
    private Set<ScheduleDto> periods;
}
