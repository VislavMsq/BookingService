package com.project_service.exception;

public class PriceCategoryNotFoundException extends RuntimeException {
    public PriceCategoryNotFoundException(String message) {
        super(message);
    }
}
