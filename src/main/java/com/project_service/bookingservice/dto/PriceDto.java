package com.project_service.bookingservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PriceDto {
    private String id;
    private Double price;
    private LocalDate date;
    private Boolean isEditedPrice;
    private String apartmentId;
    private String priority;
    private String currencyCode;
    private String currencyName;
}
