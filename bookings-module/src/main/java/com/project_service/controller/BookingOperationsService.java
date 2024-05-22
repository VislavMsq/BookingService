package com.project_service.controller;

import com.project_service.dto.BookingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public interface BookingOperationsService {

    @Operation(
            summary = "Find booking by ID",
            description = "Find a specific booking by its ID",
            tags = {"BOOKING"},
            parameters = {
                    @Parameter(
                            name = "id",
                            required = true,
                            description = "The ID of the booking",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The booking details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookingDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    BookingDto findById(@PathVariable("id") String id);

    @Operation(
            summary = "Create a new booking",
            description = "Create a new booking with the provided details",
            tags = {"BOOKING"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The details of the booking to be created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookingDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The created booking details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookingDto.class)
                            )
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('OWNER','WORKER')")
    BookingDto createBooking(@RequestBody BookingDto bookingDto);
}
