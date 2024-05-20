package com.project_service.service.impl;

import com.project_service.creator.EmailCreator;
import com.project_service.creator.impl.ActivationEmailCreator;
import com.project_service.creator.impl.ForgotPasswordEmailCreator;
import com.project_service.entity.User;
import com.project_service.exception.*;
import com.project_service.model.Mail;
import com.project_service.repository.UserRepository;
import com.project_service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(EmailServiceImpl.class);
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void resendActivationCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
        int code = generateCode();
        user.setActivationCode(code);
        user.setExpirationTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
        userRepository.save(user);

        EmailCreator emailcreator = new ActivationEmailCreator();
        Mail mail = emailcreator.createMail(user);
        sendMail(mail);
    }

    @Override
    @Transactional
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
        int code = generateCode();
        user.setActivationCode(code);
        user.setExpirationTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)));
        userRepository.save(user);

        EmailCreator emailcreator = new ForgotPasswordEmailCreator();
        Mail mail = emailcreator.createMail(user);
        sendMail(mail);
    }

    private static int generateCode() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(900000) + 100000;
    }

    @Async
    public void sendMail(Mail mail) {
        final Context ctx = new Context();
        ctx.setVariables(mail.getTemplateVars());

        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(mail.getSubject());
            message.setTo(mail.getRecipientArray());

            final String htmlContent = this.templateEngine.process(mail.getTemplate(), ctx);
            message.setText(htmlContent, true);
        } catch (MessagingException messagingException) {
            LOGGER.error(messagingException);
        }
        this.javaMailSender.send(mimeMessage);
    }
}
