package com.habilisoft.doce.api.auth.services;

import com.habilisoft.doce.api.auth.dto.CreateUserRequest;
import com.habilisoft.doce.api.auth.dto.RecoverChangePasswordRequest;
import com.habilisoft.doce.api.auth.exceptions.InvalidOldPasswordException;
import com.habilisoft.doce.api.auth.model.User;
import com.habilisoft.doce.api.auth.repositories.UserRepository;
import com.habilisoft.doce.api.auth.utils.AuthUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *
 */
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final PasswordEncoder passwordEncoder,
                       final UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }


    @Transactional
    public User createUser(CreateUserRequest request) {

        User user = User.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getEmail().toLowerCase())
                .name(request.getName())
                .verified(request.getVerified())
                .active(request.getActive())
                .recoveryToken(request.getActivationToken())
                .profileImageUrl(request.getProfileImageUrl())
                .build();

        userRepository.save(user);

        return user;
    }

    public Boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void changePassword(String oldPassword, String newPassword) throws InvalidOldPasswordException {

        User currentUser = AuthUtils.getCurrentUser();

        if (passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(currentUser);
            return;
        }

        throw new InvalidOldPasswordException();

    }


    public void changePassword(RecoverChangePasswordRequest request) {
        User user = userRepository.findByRecoveryToken(request.getToken())
                .orElseThrow();

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setRecoveryToken(null);
        userRepository.save(user);

    }


    public void updateLastLogin(User user) {
        user.setLastLogin(new Date());
        userRepository.save(user);
    }
}
