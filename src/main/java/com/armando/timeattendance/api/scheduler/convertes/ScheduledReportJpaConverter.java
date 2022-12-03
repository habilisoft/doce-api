package com.armando.timeattendance.api.scheduler.convertes;

import com.armando.timeattendance.api.persistence.converters.JpaConverter;
import com.armando.timeattendance.api.scheduler.entitites.ScheduledReportEntity;
import com.armando.timeattendance.api.scheduler.model.ScheduledReport;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 3/12/22.
 */
@Component
public class ScheduledReportJpaConverter implements JpaConverter<ScheduledReport, ScheduledReportEntity> {
    @Override
    public ScheduledReport fromJpaEntity(ScheduledReportEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(i -> ScheduledReport.builder()
                        .id(i.getId())
                        .report(i.getReport())
                        .description(i.getDescription())
                        .recipients(i.getRecipients())
                        .scheduleEntries(i.getScheduleEntries())
                        .build()
                )
                .orElse(null);

    }

    @Override
    public ScheduledReportEntity toJpaEntity(ScheduledReport domainObject) {
        return Optional.ofNullable(domainObject)
                .map(i -> ScheduledReportEntity.builder()
                        .id(i.getId())
                        .report(i.getReport())
                        .description(i.getDescription())
                        .recipients(i.getRecipients())
                        .scheduleEntries(i.getScheduleEntries())
                        .build()
                )
                .orElse(null);
    }
}
