package com.project_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank(message = "Email can not be empty")
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*\\p{Punct})[\\p{L}\\d\\p{Punct}]*$",
            message = "The password is incorrect. Password is required to contain "
                    + "at least one uppercase and one lowercase, also one digit and one special character")
    private String password;

    @Pattern(regexp = "^\\+?\\d[\\d\\-\\s]{6,14}\\d$", message = "The phone number can starts with '+', "
            + "and contain only numbers, hyphens and spaces")
    private String phone;

    @Max(14)
    @Min(-12)
    private Integer timezone;

    @NotBlank(message = "First name can not be empty")
    @Pattern(regexp = "^\\p{L}+$", message = "First name must contain only letters")
    private String firstName;

    @Pattern(regexp = "^\\p{L}+$", message = "Last name must contain only letters")
    private String lastName;

    private String id;
    private String ownerId;
    private String currencyCode;
    private String country;
    private String language;
    private String role;
}
