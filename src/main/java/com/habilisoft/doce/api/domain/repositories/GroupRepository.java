package com.habilisoft.doce.api.domain.repositories;

import com.habilisoft.doce.api.domain.model.Group;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface GroupRepository {
    Group save(Group group);
    Optional<Group> findByName(String name);
    Optional<Group> findById(Long id);
}
