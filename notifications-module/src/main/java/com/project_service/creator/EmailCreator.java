package com.project_service.creator;

import com.project_service.entity.User;
import com.project_service.model.Mail;

public interface EmailCreator {

    Mail createMail(User user);

}
