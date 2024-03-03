package com.project_servise.bookingservice.dto;

import lombok.Data;

@Data
public class ApartmentDTO {
    private String apartmentCategoryId;
    private String name;
    private String city;
    private String street;
    private String country;

}
