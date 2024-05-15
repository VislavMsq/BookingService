package com.project_service.dto;

import lombok.Data;

@Data
public class UpdatePasswordDto {
    private String email;
    private String resetCode;
    private String newPassword;
}
