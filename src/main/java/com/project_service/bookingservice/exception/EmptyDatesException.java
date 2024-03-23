package com.project_service.bookingservice.exception;

public class EmptyDatesException extends EntityNotFoundException {
    public EmptyDatesException(String message) {
        super(message);
    }
}
