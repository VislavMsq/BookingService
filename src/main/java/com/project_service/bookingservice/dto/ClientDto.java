package com.project_service.bookingservice.dto;

import lombok.Data;

@Data
public class ClientDto {
    private String id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String country;
    private String language;
    private String commentText;
}
