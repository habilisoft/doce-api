package com.armando.timeattendance.api.scheduler.services;

import com.armando.timeattendance.api.scheduler.model.ScheduledReport;
import com.armando.timeattendance.api.scheduler.model.SendReportTask;
import com.armando.timeattendance.api.scheduler.repositories.ScheduledReportRepository;
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

    public ScheduledReport update(ScheduledReport report) {
        report = repository.save(report);

        taskService.deleteReminders(report);
        List<SendReportTask> tasks = taskService.createReminders(report);
        scheduler.scheduleReminders(tasks);
        return report;
    }
}
