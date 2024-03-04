package com.project_servise.bookingservice.controller;

import com.project_servise.bookingservice.dto.UserDto;
import com.project_servise.bookingservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody UserDto userDto) {
        return userService.create(userDto).getId().toString();
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }
}
