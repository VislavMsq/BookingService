package com.project_servise.bookingservice.exception;

public class ApartmentNotFoundException extends EntityNotFoundException {
    public ApartmentNotFoundException(String message) {
        super(message);
    }
}
