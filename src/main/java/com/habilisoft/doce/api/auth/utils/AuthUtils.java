package com.habilisoft.doce.api.auth.utils;

import com.habilisoft.doce.api.auth.model.User;
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
