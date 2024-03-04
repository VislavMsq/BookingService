package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.BookingDto;
import com.project_service.bookingservice.entity.Booking;

public interface BookingService {

    Booking createBooking(BookingDto bookingDto);

    BookingDto findById(String id);
}
