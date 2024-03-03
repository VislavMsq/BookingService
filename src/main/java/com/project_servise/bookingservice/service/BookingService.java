package com.project_servise.bookingservice.service;

import com.project_servise.bookingservice.dto.BookingDto;
import com.project_servise.bookingservice.entity.Booking;

public interface BookingService {

    Booking createBooking(BookingDto bookingDto);

    BookingDto findById(String id);
}
