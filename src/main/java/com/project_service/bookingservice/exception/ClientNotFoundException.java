package com.project_service.bookingservice.exception;

public class ClientNotFoundException extends EntityNotFoundException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
