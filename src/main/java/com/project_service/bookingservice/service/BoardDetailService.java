package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.BoardDetailDto;
import com.project_service.bookingservice.dto.BoardDetailsFilterDto;
import com.project_service.bookingservice.entity.Booking;

import java.util.List;

public interface BoardDetailService {
    void createBoardOfDetail(Booking booking);

    List<BoardDetailDto> findAllBoardDetailDto(BoardDetailsFilterDto boardDetailsFilterDto);
}
