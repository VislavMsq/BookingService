package com.project_service.controller;

import com.project_service.dto.PriceCategoryToApartmentCategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface PriceCategoryToApartmentCategoryOperationsService {

    @Operation(
            summary = "Create a new price to apartment category",
            description = "Create a new price to apartment category with the provided details",
            tags = {"PRICE_TO_APARTMENT_CATEGORY"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The details of the price to apartment category to be created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceCategoryToApartmentCategoryDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The created price to apartment category details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PriceCategoryToApartmentCategoryDto.class)
                            )
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PriceCategoryToApartmentCategoryDto create(@RequestBody PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto);

    @Operation(
            summary = "Find price to apartment category by ID",
            description = "Find a specific price to apartment category by its ID",
            tags = {"PRICE_TO_APARTMENT_CATEGORY"},
            parameters = {
                    @Parameter(
                            name = "id",
                            required = true,
                            description = "The ID of the price to apartment category",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The price to apartment category details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PriceCategoryToApartmentCategoryDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    PriceCategoryToApartmentCategoryDto findPriceToApartment(@PathVariable String id);
}
