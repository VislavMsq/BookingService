package com.project_service.controller;

import com.project_service.dto.UpdatePasswordDto;
import com.project_service.dto.UserDto;
import com.project_service.service.UserService;
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
        return userService.registerUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @PutMapping("/activate")
    public void activateUser(@RequestBody String activationCode){
        userService.activateUser(activationCode);
    }

    @PutMapping("/change_password")
    public void updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
        userService.updatePassword(updatePasswordDto);
    }
}
