package com.habilisoft.doce.api.scheduler.convertes;

import com.habilisoft.doce.api.persistence.converters.JpaConverter;
import com.habilisoft.doce.api.persistence.scheduler.entitites.ScheduledReportEntity;
import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.persistence.converters.ReportJpaConverter;
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
    private final ReportJpaConverter reportJpaConverter;

    @Override
    public ScheduledReport fromJpaEntity(ScheduledReportEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(i -> ScheduledReport.builder()
                        .id(i.getId())
                        .report(
                                Optional.ofNullable(jpaEntity.getReport())
                                        .map(r -> Report.builder()
                                                .name(r.getName())
                                                .id(r.getId())
                                                .build()
                                        ).orElse(null)
                        )
                        .description(i.getDescription())
                        .recipients(i.getRecipients())
                        .scheduleEntries(i.getScheduleEntries())
                        .userFilters(i.getUserFilters())
                        .reportFormat(i.getReportFormat())
                        .build()
                )
                .orElse(null);
    }

    @Override
    public ScheduledReportEntity toJpaEntity(ScheduledReport domainObject) {
        return Optional.ofNullable(domainObject)
                .map(i -> ScheduledReportEntity.builder()
                        .id(i.getId())
                        .report(reportJpaConverter.toJpaEntity(i.getReport()))
                        .description(i.getDescription())
                        .recipients(i.getRecipients())
                        .scheduleEntries(i.getScheduleEntries())
                        .userFilters(i.getUserFilters())
                        .reportFormat(i.getReportFormat())
                        .build()
                )
                .orElse(null);
    }
}
