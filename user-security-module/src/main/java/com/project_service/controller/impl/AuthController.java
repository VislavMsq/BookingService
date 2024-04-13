package com.project_service.controller.impl;

import com.project_service.controller.AuthOperationsService;
import com.project_service.dto.UserCredentialsDto;
import com.project_service.entity.User;
import com.project_service.entity.enums.Role;
import com.project_service.security.jwt.JwtService;
import com.project_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthController implements AuthOperationsService {
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
