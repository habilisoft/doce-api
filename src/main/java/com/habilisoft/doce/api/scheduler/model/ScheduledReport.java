package com.habilisoft.doce.api.scheduler.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.domain.model.Report;
import com.habilisoft.doce.api.serialization.BaseEnumConverter;
import com.habilisoft.doce.api.utils.export.enums.ExportType;
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
    @JsonSerialize(converter = BaseEnumConverter.class)
    private Report report;
    private List<String> recipients;
    private List<ReportScheduleEntry> scheduleEntries;
    //private List<Group> groups;
    private Group group;
    private ExportType reportFormat;
    private Boolean previousDateData;
}
