package com.project_service.controller;

import com.project_service.dto.CurrencyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface CurrencyOperationsService {

    @Operation(
            summary = "Get all currencies",
            description = "Get a list of all available currencies",
            tags = {"CURRENCY"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The list of currencies",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = CurrencyDto.class))
                            )
                    )
            }
    )
    @GetMapping
    List<CurrencyDto> getAllCurrency();
}