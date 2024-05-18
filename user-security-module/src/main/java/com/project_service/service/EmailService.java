package com.project_service.service;

import com.project_service.entity.User;
import com.project_service.entity.enums.EmailType;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendActivationEmail(String to, String activationCode) throws MessagingException;

    void sendPasswordResetEmail(String to, String resetCode) throws MessagingException;

    void resendActivationCode();

    void initiatePasswordReset(String email);

    void generateAndSendCode(User user, String email, EmailType emailType, int expirationMinutes);
}
