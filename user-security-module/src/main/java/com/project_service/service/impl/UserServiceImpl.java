package com.project_service.service.impl;

import com.project_service.dto.UpdatePasswordDto;
import com.project_service.dto.UserCredentialsDto;
import com.project_service.dto.UserDto;
import com.project_service.entity.Currency;
import com.project_service.entity.User;
import com.project_service.entity.enums.EmailType;
import com.project_service.entity.enums.Role;
import com.project_service.entity.enums.Status;
import com.project_service.exception.*;
import com.project_service.mapper.UserMapper;
import com.project_service.repository.CurrencyRepository;
import com.project_service.repository.UserRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.EmailService;
import com.project_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final EmailService emailService;

    @Override
    @Transactional
    public User authenticateUser(UserCredentialsDto userCredentialsDto) {
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
    public String registerUser(UserDto userDto) {
        Currency currency = currencyRepository.findByCode(userDto.getCurrencyCode())
                .orElse(null);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.mapToEntity(userDto);
        user.setCurrency(currency);
        user.setRole(Role.OWNER);
        user.setStatus(Status.PENDING);

        emailService.generateAndSendCode(user, userDto.getEmail(), EmailType.ACTIVATION, 3);
        return String.valueOf(user.getId());
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordDto updatePasswordDto) {
        User user = userRepository.findByEmail(updatePasswordDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", updatePasswordDto.getEmail())));
        validateCode(user, updatePasswordDto.getResetCode());
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void activateUser(int activationCode) {
        User user = userProvider.getCurrentUser();
        validateCode(user, String.valueOf(activationCode));
        user.setStatus(Status.ACTIVATED);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDto getUserById(String id) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", id)));
        User currentUser = userProvider.getCurrentUser();
        if (!currentUser.equals(user) && !currentUser.equals(user.getOwner())) {
            throw new AccessDeniedException("Access denied");
        }
        return userMapper.mapToDto(user);
    }

    public static void validateCode(User user, String code) {
        if (user.getActivationCode().equals(Integer.valueOf(code))) {
            if (user.getExpirationTime().before(Timestamp.valueOf(LocalDateTime.now()))) {
                throw new ExpiredCodeException("Code has expired");
            }
        } else {
            throw new InvalidCodeException("Code is invalid");
        }
    }
}
