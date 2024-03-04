package com.project_service.bookingservice.exception;

public class CurrencyNotFoundException extends EntityNotFoundException {
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
