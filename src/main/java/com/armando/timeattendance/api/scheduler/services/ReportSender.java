package com.armando.timeattendance.api.scheduler.services;

import com.armando.timeattendance.api.scheduler.model.ScheduledReport;
import com.armando.timeattendance.api.scheduler.model.SendReportTask;
import org.springframework.stereotype.Component;

/**
 * Created on 3/12/22.
 */
@Component
public class ReportSender {

    public void sendReport(SendReportTask task) {
        ScheduledReport report = task.getScheduledReport();
        System.out.println("Sending Report");
    }
}
