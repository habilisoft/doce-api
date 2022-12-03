package com.armando.timeattendance.api.scheduler.model;

import com.armando.timeattendance.api.domain.model.Report;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created on 3/12/22.
 */
@Data
@Builder
public class ScheduledReport {
    private Long id;
    private String description;
    private Report report;
    private List<String> recipients;
    private List<ReportScheduleEntry> scheduleEntries;
}
