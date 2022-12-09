package com.habilisoft.doce.api.persistence.services;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.entities.DeviceEntity;
import com.habilisoft.doce.api.persistence.entities.EmployeeEntity;
import com.habilisoft.doce.api.persistence.repositories.DeviceJpaRepo;
import com.habilisoft.doce.api.persistence.repositories.EmployeeJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019-04-22.
 */
@Service
public class DeviceSearchService extends BaseService<DeviceEntity, Long> {
    private final DeviceJpaRepo repository;

    @Autowired
    public DeviceSearchService(final DeviceJpaRepo repository) {
        super(DeviceEntity.class);
        this.repository = repository;
    }

    @Override
    public ExtendedRepository<DeviceEntity, Long> getRepository() {
        return repository;
    }
}
