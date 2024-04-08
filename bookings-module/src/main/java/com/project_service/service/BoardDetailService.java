package com.project_service.service;

import com.project_service.dto.BoardDetailDto;
import com.project_service.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BoardDetailService {
    void createBoardOfDetail(Booking booking);

    List<BoardDetailDto> findAllBoardDetailDto(LocalDate startDate, LocalDate endDate);
}
