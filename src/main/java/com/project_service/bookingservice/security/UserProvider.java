package com.project_service.bookingservice.security;

import com.project_service.bookingservice.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserProvider {

    public User getCurrentUser() {
        return ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .user();
    }
}
