package com.project_service.service.impl;

import com.project_service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);

        String htmlContent = templateEngine.process(templateName, context);
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }

    @Override
    public void sendActivationEmail(String to, String activationCode) throws MessagingException {
        Context context = new Context();
        context.setVariable("activationCode", activationCode);
        sendEmail(to, "Activation Code", "EmailConfirmation", context);
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetCode) throws MessagingException {
        Context context = new Context();
        context.setVariable("resetCode", resetCode);
        sendEmail(to, "Reset Your Password", "PasswordRecovery", context);
    }


}
