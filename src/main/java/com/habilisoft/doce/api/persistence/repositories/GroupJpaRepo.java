package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.persistence.entities.GroupEntity;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface GroupJpaRepo extends ExtendedRepository<GroupEntity, Long> {
    Optional<GroupEntity> findByNameIgnoreCase(String name);
}
