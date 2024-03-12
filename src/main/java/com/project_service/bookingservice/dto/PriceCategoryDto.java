package com.project_service.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceCategoryDto {
    private String id;
    private String currencyCode;
    private String name;
    private String priority;
    private List<ScheduleDto> periods;
}
