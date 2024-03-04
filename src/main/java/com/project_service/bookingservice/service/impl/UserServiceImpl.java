package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.UserCredentialsDto;
import com.project_service.bookingservice.dto.UserDto;
import com.project_service.bookingservice.entity.Currency;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.entity.enums.Role;
import com.project_service.bookingservice.exception.AuthenticationException;
import com.project_service.bookingservice.exception.CurrencyNotFoundException;
import com.project_service.bookingservice.exception.UserAlreadyExistsException;
import com.project_service.bookingservice.exception.UserNotFoundException;
import com.project_service.bookingservice.mapper.UserMapper;
import com.project_service.bookingservice.repository.CurrencyRepository;
import com.project_service.bookingservice.repository.UserRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public User findByCredentials(UserCredentialsDto userCredentialsDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())) {
                return user;
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
    public User create(UserDto userDto) {
        Currency currency = currencyRepository.findByCode(userDto.getCurrencyCode())
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency with id %s not found",
                        userDto.getCurrencyCode())));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.mapToEntity(userDto);
        user.setCurrency(currency);
        user.setRole(Role.OWNER);
        user.setOwner(null);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", user.getEmail()));
        }
        return user;
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
}
