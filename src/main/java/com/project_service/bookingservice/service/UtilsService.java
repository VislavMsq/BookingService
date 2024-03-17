package com.project_service.bookingservice.service;

import com.project_service.bookingservice.entity.BaseEntity;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.exception.NotOwnerException;

public final class UtilsService {

    private UtilsService() {
    }

    public  static void checkOwner(BaseEntity entity, User user){
            if(!entity.getOwner().equals(user)){
                throw new NotOwnerException("Access denied!");
            }
    }
}
