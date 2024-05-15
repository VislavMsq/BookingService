package com.project_service.exception;

public class ExpiredCodeException extends RuntimeException{
    public ExpiredCodeException(String message) {
        super(message);
    }
}
