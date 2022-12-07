package com.habilisoft.doce.api.config.jpa;

import com.habilisoft.doce.api.auth.model.User;
import com.habilisoft.doce.api.auth.utils.AuthUtils;
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
