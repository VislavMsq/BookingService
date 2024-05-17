package com.project_service.service;

import com.project_service.dto.UpdatePasswordDto;
import com.project_service.dto.UserCredentialsDto;
import com.project_service.dto.UserDto;
import com.project_service.entity.User;
import com.project_service.entity.enums.Role;

public interface UserService {
    User authenticateUser(UserCredentialsDto userCredentialsDto);

    Role getAuthorizedUserRole();

    void registerUser(UserDto userDto);

    void resendActivationCode();

    void initiatePasswordReset(String email);

    void updatePassword(UpdatePasswordDto updatePasswordDto);

    void activateUser(int activationCode);

    UserDto getUserById(String id);
}
