package com.project_service.controller;

import com.project_service.dto.ApartmentCategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ApartmentCategoryOperationsService {

    @Operation(
            summary = "Create a new apartment category",
            description = "Create a new apartment category with the provided details",
            tags = {"APARTMENT_CATEGORY"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The details of the apartment category to be created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentCategoryDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The created apartment category details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApartmentCategoryDto.class)
                            )
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ApartmentCategoryDto createDTO(@RequestBody ApartmentCategoryDto dto);

    @Operation(
            summary = "Find apartment category by ID",
            description = "Find a specific apartment category by its ID",
            tags = {"APARTMENT_CATEGORY"},
            parameters = {
                    @Parameter(
                            name = "id",
                            required = true,
                            description = "The ID of the apartment category",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The apartment category details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApartmentCategoryDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    ApartmentCategoryDto findCategory(@PathVariable String id);

    @Operation(
            summary = "Find all apartment categories",
            description = "Find all apartment categories",
            tags = {"APARTMENT_CATEGORY"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The list of apartment categories",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApartmentCategoryDto.class)
                            )
                    )
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ApartmentCategoryDto> findAllCategories();
}