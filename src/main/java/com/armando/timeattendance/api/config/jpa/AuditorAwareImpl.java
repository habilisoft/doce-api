package com.armando.timeattendance.api.config.jpa;

import com.armando.timeattendance.api.auth.model.User;
import com.armando.timeattendance.api.auth.utils.AuthUtils;
import com.armando.timeattendance.api.persistence.entities.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        try {
            User user = AuthUtils.getCurrentUser();
            if (user == null)
                return Optional.empty();

            return Optional.of(user.getId().toString());

        } catch (Exception e){
            return Optional.empty();
        }

    }
}
