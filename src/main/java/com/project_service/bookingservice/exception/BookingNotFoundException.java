package com.project_service.bookingservice.exception;

public class BookingNotFoundException extends EntityNotFoundException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
