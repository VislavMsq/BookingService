package com.project_servise.bookingservice.exception;

public class BookingNotFoundException extends EntityNotFoundException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
