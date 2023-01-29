package com.habilisoft.doce.api.scheduler.services;

import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.model.SendReportTask;
import com.habilisoft.doce.api.persistence.scheduler.repositories.ScheduledReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 3/12/22.
 */
@Service
@RequiredArgsConstructor
public class ScheduledReportService {
    private final ReportScheduler scheduler;
    private final ScheduledReportRepository repository;
    private final DefaultSendReportTaskService taskService;

    public ScheduledReport save(ScheduledReport report) {

        report = repository.save(report);

        List<SendReportTask> tasks = taskService.createReminders(report);
        scheduler.scheduleReminders(tasks);

        return report;
    }

    public ScheduledReport update(Long reportId, ScheduledReport report) {
        report = repository.save(report);

        scheduler.removeMeetingRemindersSchedule(report);
        taskService.deleteReminders(report);
        List<SendReportTask> tasks = taskService.createReminders(report);
        scheduler.scheduleReminders(tasks);
        return report;
    }
}
