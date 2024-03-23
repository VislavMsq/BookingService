package com.project_service.bookingservice.dto;

import lombok.Data;

@Data
public class ApartmentCategoryDto {
    private String id;
    private String name;
    private String abbreviation;
    private String type;
    private String sleepPlace;
}
