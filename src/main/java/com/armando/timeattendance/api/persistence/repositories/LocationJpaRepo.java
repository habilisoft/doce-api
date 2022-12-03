package com.armando.timeattendance.api.persistence.repositories;

import com.armando.timeattendance.api.auth.base.repositories.ExtendedRepository;
import com.armando.timeattendance.api.persistence.entities.LocationEntity;

import java.util.Optional;
import java.util.stream.DoubleStream;

/**
 * Created on 11/11/22.
 */
public interface LocationJpaRepo extends ExtendedRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByNameIgnoreCase(String name);
}
