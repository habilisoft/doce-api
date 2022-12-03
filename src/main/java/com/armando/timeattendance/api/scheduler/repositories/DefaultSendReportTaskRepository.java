package com.armando.timeattendance.api.scheduler.repositories;

import com.armando.timeattendance.api.scheduler.model.ScheduledReport;
import com.armando.timeattendance.api.scheduler.model.SendReportTask;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 3/12/22.
 */
@Repository
public class DefaultSendReportTaskRepository implements SendReportTaskRepository{
    @Override
    public List<SendReportTask> getTasks() {
        return null;
    }

    @Override
    public List<SendReportTask> getTasks(ScheduledReport report) {
        return null;
    }

    @Override
    public List<SendReportTask> save(List<SendReportTask> reminders) {
        return null;
    }

    @Override
    public void deleteByScheduledReport(ScheduledReport scheduledReport) {

    }

    @Override
    public void removeDeletedReportTasks() {

    }
}
