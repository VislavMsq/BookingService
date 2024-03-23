package com.project_service.bookingservice.dto;

import lombok.Data;

@Data
public class PriceCategoryToApartmentCategoryDto {
    private String id;
    private String apartmentCategoryId;
    private String priceCategoryId;
    private String currencyCode;
    private Double price;
    private Integer year;
}
