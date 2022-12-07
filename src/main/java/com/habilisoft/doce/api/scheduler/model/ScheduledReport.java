package com.habilisoft.doce.api.scheduler.model;

import com.habilisoft.doce.api.domain.model.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 3/12/22.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledReport {
    private Long id;
    private String description;
    private Report report;
    private List<String> recipients;
    private List<ReportScheduleEntry> scheduleEntries;
}
