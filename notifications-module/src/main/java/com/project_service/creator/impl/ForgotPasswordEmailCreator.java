package com.project_service.creator.impl;

import com.project_service.creator.EmailCreator;
import com.project_service.entity.User;
import com.project_service.model.Mail;

public class ForgotPasswordEmailCreator implements EmailCreator {

    @Override
    public Mail createMail(User user) {
        Mail mail = new Mail("Reset Code", "PasswordRecovery", new String[]{user.getEmail()});
        mail.addVariable("activation-code", user.getActivationCode());
        return mail;
    }
}
