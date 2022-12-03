package com.armando.timeattendance.api.persistence.users.converters;

import com.armando.timeattendance.api.auth.model.User;
import com.armando.timeattendance.api.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 2021-01-14.
 */
@Component
public class UserJpaConverter {

    public User fromJpaEntity(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map((u) ->  User.builder()
                        .name(u.getName())
                        .id(u.getId())
                        .username(u.getUsername())
                        .profileImageUrl(u.getProfileImageUrl())
                        .lastLogin(u.getLastLogin())
                        .password(u.getPassword())
                        .createdDate(u.getCreatedDate())
                        .build())
                .orElse(null);
    }

    public UserEntity toJpaEntity(User user) {
        return Optional.ofNullable(user)
                .map((u) ->  UserEntity.builder()
                        .name(u.getName())
                        .id(u.getId())
                        .username(u.getUsername())
                        .password(u.getPassword())
                        .profileImageUrl(u.getProfileImageUrl())
                        .lastLogin(u.getLastLogin())
                        .build())
                .orElse(null);
    }
}
