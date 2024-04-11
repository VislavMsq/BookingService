package com.project_service.exception;

public class ApartmentCategoryNotFoundException extends RuntimeException {
    public ApartmentCategoryNotFoundException(String message) {
        super(message);
    }
}
