package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.persistence.entities.WorkShiftEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 2019-04-27.
 */
@Repository
public interface WorkShiftJpaRepo extends ExtendedRepository<WorkShiftEntity, Long> {
    Optional<WorkShiftEntity> findByNameIgnoreCase(String name);
}
