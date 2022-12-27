package com.habilisoft.doce.api.scheduler.convertes;

import com.habilisoft.doce.api.persistence.converters.GroupJpaConverter;
import com.habilisoft.doce.api.persistence.converters.JpaConverter;
import com.habilisoft.doce.api.persistence.scheduler.entitites.ScheduledReportEntity;
import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 3/12/22.
 */
@Component
@RequiredArgsConstructor
public class ScheduledReportJpaConverter implements JpaConverter<ScheduledReport, ScheduledReportEntity> {
    private final GroupJpaConverter groupJpaConverter;

    @Override
    public ScheduledReport fromJpaEntity(ScheduledReportEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(i -> ScheduledReport.builder()
                        .id(i.getId())
                        .report(i.getReport())
                        .description(i.getDescription())
                        .recipients(i.getRecipients())
                        .scheduleEntries(i.getScheduleEntries())
                        .reportFormat(i.getReportFormat())
                        .group(groupJpaConverter.fromJpaEntity(i.getGroup()))
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
                        .group(groupJpaConverter.toJpaEntity(i.getGroup()))
                        .scheduleEntries(i.getScheduleEntries())
                        .reportFormat(i.getReportFormat())
                        .build()
                )
                .orElse(null);
    }
}
