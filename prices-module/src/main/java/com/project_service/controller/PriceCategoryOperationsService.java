package com.project_service.controller;

import com.project_service.dto.PriceCategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PriceCategoryOperationsService {

    @Operation(
            summary = "Create a new price category",
            description = "Create a new price category with the provided details",
            tags = {"PRICE_CATEGORY"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The details of the price category to be created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceCategoryDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The created price category details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PriceCategoryDto.class)
                            )
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PriceCategoryDto createPriceOfCategory(@RequestBody PriceCategoryDto priceCategoryDto);

    @Operation(
            summary = "Find price category by ID",
            description = "Find a specific price category by its ID",
            tags = {"PRICE_CATEGORY"},
            parameters = {
                    @Parameter(
                            name = "id",
                            required = true,
                            description = "The ID of the price category",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The price category details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PriceCategoryDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    PriceCategoryDto findById(@PathVariable("id") String id);

    @Operation(
            summary = "Find all price categories",
            description = "Find all price categories",
            tags = {"PRICE_CATEGORY"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The list of price categories",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = PriceCategoryDto.class))
                            )
                    )
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<PriceCategoryDto> findAll();

    @Operation(
            summary = "Update a price category",
            description = "Update a price category with the provided details",
            tags = {"PRICE_CATEGORY"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The updated details of the price category",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceCategoryDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The updated price category details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PriceCategoryDto.class)
                            )
                    )
            }
    )
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    PriceCategoryDto update(@RequestBody PriceCategoryDto priceCategoryDto);
}