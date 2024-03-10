package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.BoardDetailsOfRangeDto;
import com.project_service.bookingservice.entity.Booking;

public interface BoardDetailService {
    void createBoardOfDetail(Booking booking);
    BoardDetailsOfRangeDto findAllBoardDetailDto(BoardDetailsOfRangeDto boardDetailsOfRangeDto);
}
