package com.armando.timeattendance.api.scheduler.repositories;

import com.armando.timeattendance.api.scheduler.convertes.ScheduledReportJpaConverter;
import com.armando.timeattendance.api.scheduler.entitites.ScheduledReportEntity;
import com.armando.timeattendance.api.scheduler.model.ScheduledReport;
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
