package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.BookingDto;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto);

    BookingDto findById(String id);
}
