package com.project_service.bookingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApartmentCategoryCreateDTO {
    private String name;
    private String abbreviation;
    private String type;
    private String sleepPlace;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
