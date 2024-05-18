package com.project_service.service;

public interface EmailService {
    void resendActivationCode(String email);

    void initiatePasswordReset(String email);
}
