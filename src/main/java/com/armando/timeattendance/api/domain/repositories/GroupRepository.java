package com.armando.timeattendance.api.domain.repositories;

import com.armando.timeattendance.api.domain.model.Group;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface GroupRepository {
    Group save(Group group);
    Optional<Group> findByName(String name);
}
