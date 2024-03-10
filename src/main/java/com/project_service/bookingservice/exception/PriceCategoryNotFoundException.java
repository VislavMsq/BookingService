package com.project_service.bookingservice.exception;

public class PriceCategoryNotFoundException extends EntityNotFoundException {
    public PriceCategoryNotFoundException(String message) {
        super(message);
    }
}
