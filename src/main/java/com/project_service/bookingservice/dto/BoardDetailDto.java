package com.project_service.bookingservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BoardDetailDto {
    private String id;
    private String bookingId;
    private String apartmentId;
    private String currencyCode;
    private Double price;
    private LocalDate date;
    private String clientName;
    private String apartmentCity;
    private Double apartmentSleepingPlace;
}
