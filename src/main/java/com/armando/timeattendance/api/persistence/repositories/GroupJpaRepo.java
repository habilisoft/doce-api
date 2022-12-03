package com.armando.timeattendance.api.persistence.repositories;

import com.armando.timeattendance.api.auth.base.repositories.ExtendedRepository;
import com.armando.timeattendance.api.persistence.entities.GroupEntity;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface GroupJpaRepo extends ExtendedRepository<GroupEntity, Long> {
    Optional<GroupEntity> findByNameIgnoreCase(String name);
}
