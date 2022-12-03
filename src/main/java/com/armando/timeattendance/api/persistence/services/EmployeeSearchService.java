package com.armando.timeattendance.api.persistence.services;

import com.armando.timeattendance.api.auth.base.repositories.ExtendedRepository;
import com.armando.timeattendance.api.auth.base.services.BaseService;
import com.armando.timeattendance.api.persistence.entities.EmployeeEntity;
import com.armando.timeattendance.api.persistence.repositories.EmployeeJpaRepo;
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
