package com.armando.timeattendance.api.persistence.services;

import com.armando.timeattendance.api.auth.base.repositories.ExtendedRepository;
import com.armando.timeattendance.api.auth.base.services.BaseService;
import com.armando.timeattendance.api.persistence.entities.WorkShiftEntity;
import com.armando.timeattendance.api.persistence.repositories.WorkShiftJpaRepo;
import org.springframework.stereotype.Service;

/**
 * Created on 11/11/22.
 */
@Service
public class WorkShiftSearchService extends BaseService<WorkShiftEntity, Long> {
    private final WorkShiftJpaRepo jpaRepo;

    public WorkShiftSearchService(WorkShiftJpaRepo jpaRepo) {
        super(WorkShiftEntity.class);
        this.jpaRepo = jpaRepo;
    }

    @Override
    public ExtendedRepository<WorkShiftEntity, Long> getRepository() {
        return jpaRepo;
    }
}
