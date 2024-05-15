package com.project_service.service;

import com.project_service.dto.UpdatePasswordDto;
import com.project_service.dto.UserCredentialsDto;
import com.project_service.dto.UserDto;
import com.project_service.entity.User;
import com.project_service.entity.enums.Role;

public interface UserService {
    User findByCredentials(UserCredentialsDto userCredentialsDto);

    Role getAuthorizedUserRole();

    void create(UserDto userDto);

    void resendActivationCode();

    void resetPassword(String email);

    void updatePassword(UpdatePasswordDto updatePasswordDto);

    void activateUser(int activationCode);

    UserDto findById(String id);
}
