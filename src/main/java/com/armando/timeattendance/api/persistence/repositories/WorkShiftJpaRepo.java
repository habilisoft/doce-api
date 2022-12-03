package com.armando.timeattendance.api.persistence.repositories;

import com.armando.timeattendance.api.auth.base.repositories.ExtendedRepository;
import com.armando.timeattendance.api.persistence.entities.WorkShiftEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.DoubleStream;

/**
 * Created on 2019-04-27.
 */
@Repository
public interface WorkShiftJpaRepo extends ExtendedRepository<WorkShiftEntity, Long> {
    Optional<WorkShiftEntity> findByNameIgnoreCase(String name);
}
