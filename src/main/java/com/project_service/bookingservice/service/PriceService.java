package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.BoardDetailsFilterDto;
import com.project_service.bookingservice.dto.BookingDto;
import com.project_service.bookingservice.dto.PriceDto;

import java.util.List;

public interface PriceService {
    List<PriceDto> getPricesOfApartment(BookingDto bookingDto);

    void updatePrices(BoardDetailsFilterDto boardDetailsFilterDto);
}

