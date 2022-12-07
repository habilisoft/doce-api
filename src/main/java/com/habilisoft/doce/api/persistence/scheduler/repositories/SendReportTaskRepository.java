package com.habilisoft.doce.api.persistence.scheduler.repositories;

import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.model.SendReportTask;

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
