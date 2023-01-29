package com.habilisoft.doce.api.scheduler.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.export.UserFilter;
import com.habilisoft.doce.api.reporting.export.enums.ExportType;
import com.habilisoft.doce.api.serialization.BaseEnumConverter;
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
    private ExportType reportFormat;
    private Boolean previousDateData;
    private List<UserFilter> userFilters;
}
