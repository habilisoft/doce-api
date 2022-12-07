package com.habilisoft.doce.api.persistence.scheduler.repositories;

import com.habilisoft.doce.api.scheduler.convertes.SendReportTaskJpaConverter;
import com.habilisoft.doce.api.persistence.scheduler.entitites.SendReportTaskEntity;
import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.model.SendReportTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 3/12/22.
 */
@Repository
@RequiredArgsConstructor
public class DefaultSendReportTaskRepository implements SendReportTaskRepository {
    private final SendReportTaskJpaRepo jpaRepo;
    private final SendReportTaskJpaConverter converter;

    @Override
    public List<SendReportTask> getTasks() {
        return jpaRepo.findAll()
                .stream()
                .map(converter::fromJpaEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SendReportTask> getTasks(ScheduledReport report) {
        return jpaRepo.findAllByScheduledReportId(report.getId())
                .stream()
                .map(converter::fromJpaEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SendReportTask> save(List<SendReportTask> reportTasks) {
        List<SendReportTaskEntity> taskEntities =
                reportTasks.stream()
                        .map(converter::toJpaEntity)
                        .collect(Collectors.toList());
        jpaRepo.saveAll(taskEntities);

        return taskEntities.stream()
                .map(converter::fromJpaEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByScheduledReport(ScheduledReport scheduledReport) {
        jpaRepo.deleteAllByScheduledReportId(scheduledReport.getId());
    }

    @Override
    @Transactional
    public void removeDeletedReportTasks() {
        jpaRepo.deleteByDeletedReports();
    }
}
