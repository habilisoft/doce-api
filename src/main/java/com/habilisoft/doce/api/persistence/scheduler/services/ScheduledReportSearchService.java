package com.habilisoft.doce.api.persistence.scheduler.services;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.scheduler.entitites.ScheduledReportEntity;
import com.habilisoft.doce.api.persistence.scheduler.repositories.ScheduledReportJpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 11/12/22.
 */
@Service
public class ScheduledReportSearchService extends BaseService<ScheduledReportEntity, Long> {
    private final ScheduledReportJpaRepository repository;

    public ScheduledReportSearchService(ScheduledReportJpaRepository repository) {
        super(ScheduledReportEntity.class);
        this.repository = repository;
    }

    @Override
    public ExtendedRepository<ScheduledReportEntity, Long> getRepository() {
        return repository;
    }
}
