package com.project_service.controller;

import com.project_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/emails")
public class EmailController {
    private final EmailService emailService;

    @PutMapping("/send_code")
    public void resendActivationCode(@RequestParam(value = "email") String email) {
        emailService.resendActivationCode(email);
    }

    @PutMapping("/forgot_password")
    public void resetPassword(@RequestParam(value = "email") String email) {
        emailService.initiatePasswordReset(email);
    }
}

