package com.habilisoft.doce.api.auth.config;

import com.habilisoft.doce.api.auth.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Created on 2020-08-07.
 */
public class CustomAuditorAware implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        try {

            return Optional.ofNullable((User) authentication.getPrincipal());
        } catch (Exception e){
            return Optional.empty();
        }
    }
}
