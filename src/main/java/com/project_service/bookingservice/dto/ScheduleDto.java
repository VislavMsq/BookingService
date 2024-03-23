package com.project_service.bookingservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
