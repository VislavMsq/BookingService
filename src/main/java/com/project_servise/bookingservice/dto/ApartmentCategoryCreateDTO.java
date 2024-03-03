package com.project_servise.bookingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApartmentCategoryCreateDTO {
    private String name;
    private String abbreviation;
    private String type;
    private String sleepPlace;
}
