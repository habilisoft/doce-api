package com.habilisoft.doce.api.persistence.services;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.entities.TimeAttendanceRecordEntity;
import com.habilisoft.doce.api.persistence.repositories.TimeAttendanceRecordJpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 14/12/22.
 */
@Service
public class TimeAttendanceRecordSearchService extends BaseService<TimeAttendanceRecordEntity, Long> {
    public TimeAttendanceRecordSearchService( TimeAttendanceRecordJpaRepository repository) {
        super(TimeAttendanceRecordEntity.class);
        this.repository = repository;
    }

    private final TimeAttendanceRecordJpaRepository repository;

    @Override
    public ExtendedRepository<TimeAttendanceRecordEntity, Long> getRepository() {
        return repository;
    }
}
