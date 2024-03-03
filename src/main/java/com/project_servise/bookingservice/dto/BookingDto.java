package com.project_servise.bookingservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDto {
    private String id;
    private String apartmentId;
    private String clientId;
    private String currencyId;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Boolean isEditedPrice;
}
