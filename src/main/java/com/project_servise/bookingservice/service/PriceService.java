package com.project_servise.bookingservice.service;

import com.project_servise.bookingservice.dto.BookingDto;
import com.project_servise.bookingservice.dto.PriceDto;

import java.util.List;

public interface PriceService {
    List<PriceDto> getPricesOfApartment(BookingDto bookingDto);
}
