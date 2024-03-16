package com.project_service.bookingservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BoardDetailsFilterDto {
    private LocalDate start;
    private LocalDate finish;
    private String apartmentId;
}
