package com.habilisoft.doce.api.persistence.scheduler.repositories;

import com.habilisoft.doce.api.scheduler.convertes.ScheduledReportJpaConverter;
import com.habilisoft.doce.api.persistence.scheduler.entitites.ScheduledReportEntity;
import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created on 3/12/22.
 */
@Service
@RequiredArgsConstructor
public class ScheduledReportRepository {
    private final ScheduledReportJpaRepository jpaRepo;
    private final ScheduledReportJpaConverter converter;

    public ScheduledReport save(ScheduledReport report) {
        ScheduledReportEntity entity = converter.toJpaEntity(report);
        jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }
}
