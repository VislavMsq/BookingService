package com.project_service.service.impl;


import com.project_service.dto.UpdatePasswordDto;
import com.project_service.dto.UserCredentialsDto;
import com.project_service.dto.UserDto;
import com.project_service.entity.Currency;
import com.project_service.entity.User;
import com.project_service.entity.enums.Role;
import com.project_service.entity.enums.Status;
import com.project_service.exception.*;
import com.project_service.mapper.UserMapper;
import com.project_service.repository.CurrencyRepository;
import com.project_service.repository.UserRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final UserProvider userProvider;
    private final UserMapper userMapper;
    private final MailService mailService;

    @Override
    @Transactional
    public User findByCredentials(UserCredentialsDto userCredentialsDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())) {
                if (user.getStatus().equals(Status.ACTIVATED)) {
                    return user;
                } else {
                    throw new AuthenticationException("User not activated");
                }
            }
        }
        throw new AuthenticationException("Email or password is not correct");

    }

    @Override
    public Role getAuthorizedUserRole() {
        return userProvider.getCurrentUser().getRole();
    }

    @Override
    @Transactional
    public void create(UserDto userDto) {
        Currency currency = currencyRepository.findByCode(userDto.getCurrencyCode())
                .orElse(null);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.mapToEntity(userDto);
        user.setCurrency(currency);
        user.setRole(Role.OWNER);
        user.setStatus(Status.PENDING);

        int activationCode = generateCode();
        user.setActivationCode(activationCode);
        user.setExpirationTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(3)));

        try {
            userRepository.save(user);
            mailService.sendActivationEmail(userDto.getEmail(), String.valueOf(activationCode));
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", user.getEmail()));
        } catch (MessagingException e) {
            throw new MailSendingException("Mail sending failed", e);
        }
    }


    @Override
    @Transactional
    public void resendActivationCode() {
        User user = userProvider.getCurrentUser();
        int activationCode = generateCode();
        user.setActivationCode(activationCode);
        user.setExpirationTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(3)));

        try {
            userRepository.save(user);

            mailService.sendActivationEmail(user.getEmail(), String.valueOf(activationCode));
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", user.getEmail()));
        } catch (MessagingException e) {
            throw new MailSendingException("Mail sending failed", e);
        }
    }

    @Override
    @Transactional
    public void resetPassword(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
        int resetCode = generateCode();
        user.setActivationCode(resetCode);
        user.setExpirationTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
        try {
            userRepository.save(user);
            mailService.sendPasswordResetEmail(user.getEmail(), String.valueOf(resetCode));
        } catch (MessagingException e) {
            throw new MailSendingException("Mail sending failed", e);
        }
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordDto updatePasswordDto) {
        User user = userRepository.findByEmail(updatePasswordDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", updatePasswordDto.getEmail())));
        if (user.getActivationCode().equals(Integer.valueOf(updatePasswordDto.getResetCode()))) {
            if (user.getExpirationTime().after(Timestamp.valueOf(LocalDateTime.now()))) {
                user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
                userRepository.save(user);
            } else {
                throw new ExpiredCodeException("Reset code has expired");
            }
        } else {
            throw new InvalidCodeException("Reset code is invalid");
        }
    }

    @Override
    @Transactional
    public void activateUser(int activationCode) {
        User user = userProvider.getCurrentUser();
        if (user.getActivationCode() == activationCode) {
            if (user.getExpirationTime().after(Timestamp.valueOf(LocalDateTime.now()))) {
                user.setStatus(Status.ACTIVATED);
                userRepository.save(user);
            } else {
                throw new ExpiredCodeException("Activation code has expired");
            }
        } else {
            throw new InvalidCodeException("Activation code is invalid");
        }
    }

    @Override
    @Transactional
    public UserDto findById(String id) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", id)));
        User currentUser = userProvider.getCurrentUser();
        if (!currentUser.equals(user) && !currentUser.equals(user.getOwner())) {
            throw new AccessDeniedException("Access denied");
        }
        return userMapper.mapToDto(user);
    }

    private int generateCode() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(900000) + 100000;
    }
}
