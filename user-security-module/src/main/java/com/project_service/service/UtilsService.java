package com.project_service.service;

import com.project_service.entity.OwnEntity;
import com.project_service.entity.User;
import com.project_service.exception.NotOwnerException;

public final class UtilsService {

    private UtilsService() {
    }

    public static void checkOwner(OwnEntity entity, User user) {
        if (!entity.getOwner().equals(user)) {
            throw new NotOwnerException("Access denied!");
        }
    }
}
