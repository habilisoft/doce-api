package com.habilisoft.doce.api.persistence.services;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.entities.WorkShiftEntity;
import com.habilisoft.doce.api.persistence.repositories.WorkShiftJpaRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<WorkShiftEntity> searchByName(String name, Pageable pageable) {
        return jpaRepo.searchByName(name, pageable);
    }
}
