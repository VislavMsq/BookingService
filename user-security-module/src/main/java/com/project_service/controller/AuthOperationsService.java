package com.project_service.controller;

import com.project_service.dto.UserCredentialsDto;
import com.project_service.entity.enums.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthOperationsService {

    @Operation(
            summary = "Authenticate user",
            description = "Authenticate a user with the provided credentials and return a JWT token",
            tags = {"AUTH"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The user's credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserCredentialsDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The JWT token",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string")
                            )
                    )
            }
    )
    @PostMapping
    String auth(@RequestBody UserCredentialsDto userCredentialsDto);

    @Operation(
            summary = "Get authorized user role",
            description = "Get the role of the currently authorized user",
            tags = {"AUTH"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The role of the authorized user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Role.class)
                            )
                    )
            }
    )
    @GetMapping
    Role auth();
}
