package com.project_service.service.impl;

import com.project_service.entity.User;
import com.project_service.entity.enums.EmailType;
import com.project_service.exception.*;
import com.project_service.repository.UserRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserRepository userRepository;
    private final UserProvider userProvider;

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

    @Override
    @Transactional
    public void resendActivationCode() {
        User user = userProvider.getCurrentUser();
        generateAndSendCode(user, user.getEmail(), EmailType.ACTIVATION, 3);
    }

    @Override
    @Transactional
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
        generateAndSendCode(user, user.getEmail(), EmailType.PASSWORD_RESET, 5);
    }


    @Override
    @Transactional

    public void generateAndSendCode(User user, String email, EmailType emailType, int expirationMinutes) {
        int code = generateCode();
        user.setActivationCode(code);
        user.setExpirationTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(expirationMinutes)));

        try {
            userRepository.save(user);
            sendEmail(email, String.valueOf(code), emailType);
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", user.getEmail()));
        } catch (MessagingException e) {
            throw new MailSendingException("Mail sending failed", e);
        }
    }

    private void sendEmail(String email, String code, EmailType emailType) throws MessagingException {
        switch (emailType) {
            case ACTIVATION:
                sendActivationEmail(email, code);
                break;
            case PASSWORD_RESET:
                sendPasswordResetEmail(email, code);
                break;
            default:
                throw new IllegalArgumentException("Invalid email type");
        }
    }

    public static int generateCode() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(900000) + 100000;
    }

}
