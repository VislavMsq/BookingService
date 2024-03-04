package com.project_service.bookingservice.service;

import com.project_service.bookingservice.dto.UserCredentialsDto;
import com.project_service.bookingservice.dto.UserDto;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.entity.enums.Role;

public interface UserService {
    User findByCredentials(UserCredentialsDto userCredentialsDto);

    Role getAuthorizedUserRole();

    User create(UserDto userDto);

    UserDto findById(String id);
}
