package com.habilisoft.doce.api.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TimeAttendanceSummaryReportDetail {
    private Date recordDate;
    private Long id;
    private String fullName;
    private String group;
    private Date time;
    private Date startTime;
}
