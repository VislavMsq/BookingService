package com.project_service.bookingservice.dto;

import lombok.Data;

@Data
public class ApartmentCategoryDTO {
    private String id;
    private String name;
    private String abbreviation;
    private String type;
    private String sleepPlace;
}
