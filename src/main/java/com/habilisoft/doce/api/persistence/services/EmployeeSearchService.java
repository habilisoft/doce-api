package com.habilisoft.doce.api.persistence.services;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.entities.EmployeeEntity;
import com.habilisoft.doce.api.persistence.repositories.EmployeeJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019-04-22.
 */
@Service
public class EmployeeSearchService extends BaseService<EmployeeEntity, Long> {
    private final EmployeeJpaRepo repository;

    @Autowired
    public EmployeeSearchService(final EmployeeJpaRepo repository) {
        super(EmployeeEntity.class);
        this.repository = repository;
    }

    @Override
    public ExtendedRepository<EmployeeEntity, Long> getRepository() {
        return repository;
    }
}
