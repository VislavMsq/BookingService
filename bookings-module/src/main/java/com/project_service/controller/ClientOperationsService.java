package com.project_service.controller;

import com.project_service.dto.ClientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface ClientOperationsService {

    @Operation(
            summary = "Find client by phone",
            description = "Find a specific client by its phone number",
            tags = {"CLIENT"},
            parameters = {
                    @Parameter(
                            name = "phone",
                            required = true,
                            description = "The phone number of the client",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The client details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDto.class)
                            )
                    )
            }
    )
    @GetMapping("/phones/{phone}")
    ClientDto findByPhone(@PathVariable("phone") String phone);

    @Operation(
            summary = "Find client by ID",
            description = "Find a specific client by its ID",
            tags = {"CLIENT"},
            parameters = {
                    @Parameter(
                            name = "id",
                            required = true,
                            description = "The ID of the client",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The client details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDto.class)
                            )
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ClientDto create(@RequestBody @Valid ClientDto clientDto);
}
