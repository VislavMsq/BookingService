package com.project_service.controller;

import com.project_service.service.UserService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/emails")
public class EmailController {
    private final UserService userService;

    @PutMapping("/resend_activation")
    public void resendActivationCode() {
        userService.resendActivationCode();
    }

    @PutMapping("/forgot_password")
    public void resetPassword(@RequestBody @Email String email) {
        userService.initiatePasswordReset(email);
    }
}
