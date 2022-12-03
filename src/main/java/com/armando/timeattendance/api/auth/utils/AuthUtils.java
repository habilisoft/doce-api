package com.armando.timeattendance.api.auth.utils;

import com.armando.timeattendance.api.auth.model.User;
import com.armando.timeattendance.api.persistence.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created on 2020-11-09.
 */
@Component
public class AuthUtils {

    public static User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
