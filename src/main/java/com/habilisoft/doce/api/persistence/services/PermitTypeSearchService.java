package com.habilisoft.doce.api.persistence.services;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.entities.PermitTypeEntity;
import com.habilisoft.doce.api.persistence.repositories.PermitTypeJpaRepo;
import org.springframework.stereotype.Service;

/**
 * Created on 18/2/23.
 */
@Service
public class PermitTypeSearchService extends BaseService<PermitTypeEntity, Long> {
    private final PermitTypeJpaRepo permitTypeJpaRepo;
    public PermitTypeSearchService(final PermitTypeJpaRepo permitTypeJpaRepo) {
        super(PermitTypeEntity.class);
        this.permitTypeJpaRepo = permitTypeJpaRepo;
    }

    @Override
    public ExtendedRepository<PermitTypeEntity, Long> getRepository() {
        return permitTypeJpaRepo;
    }
}
