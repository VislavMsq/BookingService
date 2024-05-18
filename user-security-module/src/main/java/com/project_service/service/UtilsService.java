package com.project_service.service;

import com.project_service.entity.OwnEntity;
import com.project_service.entity.User;
import com.project_service.exception.NotOwnerException;

import java.security.SecureRandom;

public final class UtilsService {

    private UtilsService() {
    }

    public static void checkOwner(OwnEntity entity, User user) {
        if (!entity.getOwner().equals(user)) {
            throw new NotOwnerException("Access denied!");
        }
    }

    public static int generateCode() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(900000) + 100000;
    }
}
