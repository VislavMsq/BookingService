package com.project_servise.bookingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateApartmentDTO {
    private String name;
    private String type;
    private String country;
    private String city;
    private String street;
    private String floor;
    private String pet;
    private String smoking;
    private String parkingPlace;
    private String description;
    private String parent;
    private String apartmentCategoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
