package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.UserDto;
import com.project_service.bookingservice.security.jwt.JwtService;
import com.project_service.bookingservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody UserDto userDto) {
        userService.create(userDto);
        return jwtService.generateToken(userDto.getEmail());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }
}
