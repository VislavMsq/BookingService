package com.project_service.controller;

import com.project_service.dto.BoardDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.time.LocalDate;
import java.util.List;

public interface BoardDetailOperationService {

    @Operation(
            summary = "Find all board details",
            description = "Find all board details within the given date range",
            tags = {"BOARD_DETAIL"},
            parameters = {
                    @Parameter(
                            name = "startDate",
                            required = true,
                            description = "The start date of the range",
                            schema = @Schema(type = "string", format = "yyyy-MM-dd")
                    ),
                    @Parameter(
                            name = "endDate",
                            required = true,
                            description = "The end date of the range",
                            schema = @Schema(type = "string", format = "yyyy-MM-dd")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The list of board details",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BoardDetailDto.class))
                            )
                    )
            }
    )
    List<BoardDetailDto> findAllBoardDetailDto(LocalDate startDate, LocalDate endDate);
}
