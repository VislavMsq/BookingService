package com.project_service.controller;


import com.project_service.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface UserOperationsService {

    @Operation(
            summary = "Create a new user",
            description = "Create a new user with the provided details and return a JWT token",
            tags = {"USER"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The details of the user to be created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The JWT token",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string")
                            )
                    )
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    String create(@Valid @RequestBody UserDto userDto);

    @Operation(
            summary = "Find user by ID",
            description = "Find a specific user by its ID",
            tags = {"USER"},
            parameters = {
                    @Parameter(
                            name = "id",
                            required = true,
                            description = "The ID of the user",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The user details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    UserDto findById(@PathVariable("id") String id);
}