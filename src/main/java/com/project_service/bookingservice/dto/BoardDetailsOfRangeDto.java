package com.project_service.bookingservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BoardDetailsOfRangeDto {
    private LocalDate start;
    private LocalDate finish;
    private List<BoardDetailDto> boardDetailDto;
    private String apartmentId;
}
