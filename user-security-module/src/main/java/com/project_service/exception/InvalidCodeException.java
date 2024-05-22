package com.project_service.exception;

public class InvalidCodeException extends RuntimeException{
    public InvalidCodeException(String message){
        super(message);
    }
}
