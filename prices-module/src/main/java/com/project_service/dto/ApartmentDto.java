package com.project_service.dto;

import lombok.Data;

@Data
public class ApartmentDto {
    private String id;
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
    private String apartmentCategoryId;
    private String parentId;
}
