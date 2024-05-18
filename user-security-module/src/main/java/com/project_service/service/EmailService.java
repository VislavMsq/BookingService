package com.project_service.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendActivationEmail(String to, String activationCode) throws MessagingException;

    void sendPasswordResetEmail(String to, String resetCode) throws MessagingException;
}
