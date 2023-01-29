package com.habilisoft.doce.api.scheduler.services;


import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.model.SendReportTask;
import com.habilisoft.doce.api.persistence.scheduler.repositories.SendReportTaskRepository;
import com.habilisoft.doce.api.tenant.model.jpa.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 2020-11-18.
 */
@Slf4j
@Component
public class ReportScheduler {
    private final SendReportTaskRepository reminderRepository;
    private final ReportSender reportSender;
    private final ConcurrentTaskScheduler scheduler;
    private final Map<SendReportTask, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    public ReportScheduler(final SendReportTaskRepository reminderRepository,
                           final ReportSender reportSender) {
        this.reminderRepository = reminderRepository;
        this.reportSender = reportSender;
        this.scheduler = new ConcurrentTaskScheduler();
    }


    public void loadReminders(Tenant tenant) {
        TenantContext.setCurrentTenant(tenant.getName());

        reminderRepository.removeDeletedReportTasks();
        reminderRepository.getTasks()
                .forEach(this::scheduleReminder);
    }

    public void scheduleReminders(List<SendReportTask> tasks) {
        tasks.forEach(this::scheduleReminder);
    }

    public void scheduleReminder(SendReportTask task) {
        String tenant = TenantContext.getCurrentTenant();
        task.setTenant(tenant);
        log.info("Scheduling Report Task {} {} {} {}",
                kv("reportId", task.getScheduledReport().getId()),
                kv("tenant", tenant),
                kv("timeZone", task.getTimeZone()),
                kv("cronExpression", task.getCronExpression()));

        if (scheduledTasks.containsKey(task)) {
            scheduledTasks.get(task)
                    .cancel(true);
        }

        ScheduledFuture<?> scheduledFuture = scheduler.schedule(
                () -> reportSender.sendReport(task),
                new CronTrigger(task.getCronExpression()));

        scheduledTasks.put(task, scheduledFuture);
    }

    void removeReminderSchedule(SendReportTask task) {
        task.setTenant(TenantContext.getCurrentTenant());
        log.info("Removing task from scheduler {}", kv("taskId", task.getId()));

        if (!scheduledTasks.containsKey(task))
            return;

        log.info("Scheduled Task found on Scheduler {}", kv("taskId", task.getId()));

        scheduledTasks.get(task).cancel(true);

        log.info("Scheduled Tasks cancelled {}", kv("taskId", task.getId()));

        scheduledTasks.remove(task);

        log.info("Scheduled Task removed {}", kv("taskId", task.getId()));
    }


    public void removeMeetingRemindersSchedule(ScheduledReport report) {

        log.info("Removing tasks from scheduler {}", kv("reportId", report.getId()));

        List<SendReportTask> reminders = reminderRepository.getTasks(report);

        reminders.forEach(this::removeReminderSchedule);
    }

    public Set<SendReportTask> getScheduledTasks() {
        return scheduledTasks.keySet();
    }
}
