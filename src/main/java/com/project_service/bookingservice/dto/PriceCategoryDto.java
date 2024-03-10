package com.project_service.bookingservice.dto;

import lombok.Data;

@Data
public class PriceCategoryDto {
    private String id;
    private String currencyCode;
    private String name;
    private String priority;
}
