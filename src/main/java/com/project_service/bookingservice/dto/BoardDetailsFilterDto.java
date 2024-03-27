package com.project_service.bookingservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BoardDetailsFilterDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> apartmentIds;
}
