package com.project_service.service;

import com.project_service.dto.BookingDto;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto);

    BookingDto findById(String id);
}
