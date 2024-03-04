package com.project_service.bookingservice.exception;

public class ApartmentNotFoundException extends EntityNotFoundException {
    public ApartmentNotFoundException(String message) {
        super(message);
    }
}
