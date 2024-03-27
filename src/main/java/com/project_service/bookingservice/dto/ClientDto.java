package com.project_service.bookingservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientDto {
    private String id;
    @Email
    private String email;
    @Pattern(regexp = "^\\d{6,14}$", message = "The phone number must contain only numbers")
    private String phone;
    private String firstName;
    private String lastName;
    private String country;
    private String language;
    private String commentText;
}
