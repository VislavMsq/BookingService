package com.project_service.bookingservice.exception;

public class ApartmentCategoryNotFoundException extends RuntimeException {
    public ApartmentCategoryNotFoundException(String message) {
        super(message);
    }
}
