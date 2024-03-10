package com.project_service.bookingservice.exception;

public class PriceNotFoundException extends EntityNotFoundException {
    public PriceNotFoundException(String message) {
        super(message);
    }
}
