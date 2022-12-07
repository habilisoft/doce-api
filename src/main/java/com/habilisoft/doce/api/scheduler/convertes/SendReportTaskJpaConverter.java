package com.habilisoft.doce.api.scheduler.convertes;

import com.habilisoft.doce.api.persistence.converters.JpaConverter;
import com.habilisoft.doce.api.persistence.scheduler.entitites.SendReportTaskEntity;
import com.habilisoft.doce.api.scheduler.model.SendReportTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 3/12/22.
 */
@Component
@RequiredArgsConstructor
public class SendReportTaskJpaConverter implements JpaConverter<SendReportTask, SendReportTaskEntity> {
    private final ScheduledReportJpaConverter reportJpaConverter;

    @Override
    public SendReportTask fromJpaEntity(SendReportTaskEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(i -> SendReportTask.builder()
                        .id(i.getId())
                        .scheduledReport(reportJpaConverter.fromJpaEntity(jpaEntity.getScheduledReport()))
                        .timeZone(i.getTimeZone())
                        .cronExpression(i.getCronExpression())
                        .build()
                )
                .orElse(null);

    }

    @Override
    public SendReportTaskEntity toJpaEntity(SendReportTask domainObject) {
        return Optional.ofNullable(domainObject)
                .map(i -> SendReportTaskEntity.builder()
                        .id(i.getId())
                        .scheduledReport(reportJpaConverter.toJpaEntity(domainObject.getScheduledReport()))
                        .timeZone(i.getTimeZone())
                        .cronExpression(i.getCronExpression())
                        .build()
                )
                .orElse(null);
    }
}
