package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.UserCredentialsDto;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.entity.enums.Role;
import com.project_service.bookingservice.security.jwt.JwtService;
import com.project_service.bookingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;


    @PostMapping
    public String auth(@RequestBody UserCredentialsDto userCredentialsDto) {
        User user = userService.findByCredentials(userCredentialsDto);
        return jwtService.generateToken(user.getEmail());
    }

    @GetMapping
    public Role auth() {
        return userService.getAuthorizedUserRole();
    }
}
