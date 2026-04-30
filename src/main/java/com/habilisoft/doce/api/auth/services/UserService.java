package com.habilisoft.doce.api.auth.services;

import com.habilisoft.doce.api.auth.dto.CreateUserRequest;
import com.habilisoft.doce.api.auth.base.exceptions.BadRequestException;
import com.habilisoft.doce.api.auth.exceptions.UsernameConflictException;
import com.habilisoft.doce.api.auth.dto.RecoverChangePasswordRequest;
import com.habilisoft.doce.api.auth.exceptions.InvalidOldPasswordException;
import com.habilisoft.doce.api.auth.model.User;
import com.habilisoft.doce.api.auth.repositories.UserRepository;
import com.habilisoft.doce.api.auth.utils.AuthUtils;
import com.habilisoft.doce.api.persistence.users.UserJpaRepository;
import com.habilisoft.doce.api.persistence.users.converters.UserJpaConverter;
import com.habilisoft.doce.api.web.users.dto.CreateUserMaintenanceRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 *
 */
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;
    private final UserJpaConverter userJpaConverter;

    public UserService(final PasswordEncoder passwordEncoder,
                       final UserRepository userRepository,
                       final UserJpaRepository userJpaRepository,
                       final UserJpaConverter userJpaConverter) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userJpaRepository = userJpaRepository;
        this.userJpaConverter = userJpaConverter;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }


    @Transactional
    public User createUser(CreateUserRequest request) {
        return createUser(request.getName(), request.getEmail(), request.getPassword());
    }

    @Transactional
    public User createUser(CreateUserMaintenanceRequest request) {
        return createUser(request.getName(), request.getUsername(), request.getPassword());
    }

    @Transactional(readOnly = true)
    public Page<User> searchUsers(Map<String, Object> queryMap, Pageable pageable) {
        return userJpaRepository.search(extractSearchTerm(queryMap), pageable)
                .map(userJpaConverter::fromJpaEntity);
    }

    @Transactional
    public User createUser(String name, String username, String password) {
        final String normalizedUsername = normalizeUsername(username);

        if (!StringUtils.hasText(normalizedUsername)) {
            throw new BadRequestException("username");
        }

        if (userRepository.findByUsername(normalizedUsername).isPresent()) {
            throw new UsernameConflictException(normalizedUsername);
        }

        User user = User.builder()
                .password(passwordEncoder.encode(password))
                .username(normalizedUsername)
                .name(name != null ? name.trim() : null)
                .verified(true)
                .active(true)
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

    private String normalizeUsername(String username) {
        return username == null ? null : username.trim().toLowerCase();
    }

    private String extractSearchTerm(Map<String, Object> queryMap) {
        if (queryMap == null || queryMap.isEmpty()) {
            return null;
        }

        Object[] keys = {"name~", "username~", "name", "username"};
        for (Object key : keys) {
            Object value = queryMap.get(key);
            if (value instanceof String && StringUtils.hasText((String) value)) {
                return ((String) value).trim();
            }
        }
        return null;
    }
}
