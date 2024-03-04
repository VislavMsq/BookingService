package com.project_servise.bookingservice.exception;

import org.springframework.security.access.AccessDeniedException;

public class NotOwnerException extends AccessDeniedException {
    public NotOwnerException(String msg) {
        super(msg);
    }
}
