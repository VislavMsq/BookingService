package com.project_service.controller;

import com.project_service.dto.PriceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

public interface PriceOperationsService {

    @Operation(
            summary = "Get prices of apartment",
            description = "Get prices of a specific apartment within a date range",
            tags = {"PRICE"},
            parameters = {
                    @Parameter(
                            name = "id",
                            required = true,
                            description = "The ID of the apartment",
                            schema = @Schema(type = "string")
                    ),
                    @Parameter(
                            name = "startDate",
                            required = true,
                            description = "The start date of the range",
                            schema = @Schema(type = "string", format = "date")
                    ),
                    @Parameter(
                            name = "endDate",
                            required = true,
                            description = "The end date of the range",
                            schema = @Schema(type = "string", format = "date")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The list of prices",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = PriceDto.class))
                            )
                    )
            }
    )
    @GetMapping("/apartment/{id}")
    List<PriceDto> getPricesOfApartment(@PathVariable("id") String apartmentId,
                                        @RequestParam("startDate") LocalDate startDate,
                                        @RequestParam("endDate") LocalDate endDate);

    @Operation(
            summary = "Update prices",
            description = "Update prices for a list of apartments within a date range",
            tags = {"PRICE"},
            parameters = {
                    @Parameter(
                            name = "startDate",
                            required = true,
                            description = "The start date of the range",
                            schema = @Schema(type = "string", format = "date")
                    ),
                    @Parameter(
                            name = "endDate",
                            required = true,
                            description = "The end date of the range",
                            schema = @Schema(type = "string", format = "date")
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
    @PutMapping
    ResponseEntity<Void> updatePrices(@RequestParam("startDate") LocalDate startDate,
                                      @RequestParam("endDate") LocalDate endDate,
                                      @RequestBody List<String> apartmentIds);
}