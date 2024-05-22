package com.project_service.controller;

import com.project_service.dto.ApartmentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ApartmentOperationsService {

    @Operation(
            summary = "Create a new apartment",
            description = "Create a new apartment with the provided details",
            tags = {"APARTMENT"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The details of the apartment to be created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The created apartment details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApartmentDto.class)
                            )
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ApartmentDto createApartment(@RequestBody ApartmentDto apartmentDTO);

    @Operation(
            summary = "Find apartment by ID",
            description = "Find a specific apartment by its ID",
            tags = {"APARTMENT"},
            parameters = {
                    @Parameter(
                            name = "id",
                            required = true,
                            description = "The ID of the apartment",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The apartment details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApartmentDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    ApartmentDto findApartment(@PathVariable String id);

    @Operation(
            summary = "Find all apartments",
            description = "Find all apartments",
            tags = {"APARTMENT"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The list of apartments",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = ApartmentDto.class))
                            )
                    )
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ApartmentDto> findAllApartments();

    @Operation(
            summary = "Find apartments by country",
            description = "Find apartments by country",
            tags = {"APARTMENT"},
            parameters = {
                    @Parameter(
                            name = "country",
                            required = true,
                            description = "The country of the apartments",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The list of apartments",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = ApartmentDto.class))
                            )
                    )
            }
    )
    @GetMapping("/country/{country}")
    List<ApartmentDto> findApartmentsByCountry(@PathVariable String country);

    @Operation(
            summary = "Find apartments by city",
            description = "Find apartments by city",
            tags = {"APARTMENT"},
            parameters = {
                    @Parameter(
                            name = "city",
                            required = true,
                            description = "The city of the apartments",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The list of apartments",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = ApartmentDto.class))
                            )
                    )
            }
    )
    @GetMapping("/city/{city}")
    List<ApartmentDto> findApartmentsByCity(@PathVariable String city);

    @Operation(
            summary = "Set apartment category",
            description = "Set apartment category for a list of apartments",
            tags = {"APARTMENT"},
            parameters = {
                    @Parameter(
                            name = "apartmentCategoryId",
                            required = true,
                            description = "The ID of the apartment category",
                            schema = @Schema(type = "string")
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The list of apartment IDs",
                    content = @Content(
                            mediaType = "application/json",
                            array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(type = "string"))
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The operation was successful"
                    )
            }
    )
    @PutMapping("/set-apartment-category/{apartmentCategoryId}")
    @ResponseStatus(HttpStatus.OK)
    void setApartmentsCategory(@RequestBody List<String> apartmentIds, @PathVariable String apartmentCategoryId);
}
