package com.habilisoft.doce.api.persistence.services;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.entities.EmployeePermitEntity;
import com.habilisoft.doce.api.persistence.repositories.EmployeePermitJpaRepo;
import org.springframework.stereotype.Service;

/**
 * Created on 19/2/23.
 */
@Service
public class EmployeePermitSearchService extends BaseService<EmployeePermitEntity, Long> {
    private final EmployeePermitJpaRepo jpaRepo;

    public EmployeePermitSearchService(EmployeePermitJpaRepo jpaRepo) {
        super(EmployeePermitEntity.class);
        this.jpaRepo = jpaRepo;
    }

    @Override
    public ExtendedRepository<EmployeePermitEntity, Long> getRepository() {
        return jpaRepo;
    }
}
