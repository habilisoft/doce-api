package com.armando.timeattendance.api.config;

import com.armando.timeattendance.api.auth.model.User;
import com.armando.timeattendance.api.auth.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 9/14/18.
 */
@Slf4j
@Component
public class StartupHousekeeper implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public StartupHousekeeper(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        List.of( "superadmin")
                .forEach(this::createUser);
    }

    @Transactional
    public void createUser(String username){

        User user = userRepository.findByUsername(username)
                .orElse(new User());

        if(user.getId() != null) {
            return;
        }

        user.setPassword(passwordEncoder.encode(username));
        user.setUsername(username);
        user.setName(username);
        userRepository.save(user);
    }

}
