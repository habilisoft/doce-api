package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.persistence.entities.LocationEntity;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface LocationJpaRepo extends ExtendedRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByNameIgnoreCase(String name);
}
