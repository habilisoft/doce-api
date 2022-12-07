package com.habilisoft.doce.api.auth.repositories;

import com.habilisoft.doce.api.auth.model.User;

import java.util.Optional;

/**
 * Created on 8/25/22.
 */
public interface UserRepository {
    User save(User user);

    User edit(User user, Boolean changeUsername);

    User findByEmail(String email);

    User findByCedula(String cedula);

    boolean userExist(String username);

    void increaseRegisteredCount(User user, int i);

    void increaseRegisteredCount(Long userId, int i);

    void deleteByCedula(String cedula);

    User findById(Long id);

    Optional<User> findByUsername(String s);

    Optional<User> findByRecoveryToken(String token);
}
