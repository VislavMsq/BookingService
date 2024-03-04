package com.project_servise.bookingservice.service;

import com.project_servise.bookingservice.dto.UserCredentialsDto;
import com.project_servise.bookingservice.dto.UserDto;
import com.project_servise.bookingservice.entity.User;
import com.project_servise.bookingservice.entity.enums.Role;

public interface UserService {
    User findByCredentials(UserCredentialsDto userCredentialsDto);

    Role getAuthorizedUserRole();

    User create(UserDto userDto);

    UserDto findById(String id);
}
