package com.armando.timeattendance.api.scheduler.repositories;

import com.armando.timeattendance.api.scheduler.model.ScheduledReport;
import com.armando.timeattendance.api.scheduler.model.SendReportTask;

import java.util.List;

/**
 * Created on 2020-11-19.
 */
public interface SendReportTaskRepository {
    List<SendReportTask> getTasks();
    List<SendReportTask> getTasks(ScheduledReport report);
    List<SendReportTask> save(List<SendReportTask> reminders);
    void deleteByScheduledReport(ScheduledReport scheduledReport);
    void removeDeletedReportTasks();
}
