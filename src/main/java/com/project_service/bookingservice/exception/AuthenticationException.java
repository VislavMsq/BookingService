package com.project_servise.bookingservice.exception;

import org.springframework.security.access.AccessDeniedException;

public class AuthenticationException extends AccessDeniedException {
    public AuthenticationException(String message) {
        super(message);
    }
}
