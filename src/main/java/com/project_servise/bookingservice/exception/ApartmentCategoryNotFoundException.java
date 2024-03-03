package com.project_servise.bookingservice.exception;

public class ApartmentCategoryNotFoundException extends RuntimeException{
    public ApartmentCategoryNotFoundException(String message) {
        super(message);
    }
}
