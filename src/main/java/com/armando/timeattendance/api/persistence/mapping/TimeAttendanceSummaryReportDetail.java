package com.armando.timeattendance.api.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TimeAttendanceSummaryReportDetail {
    private Date recordDate;
    private Long id;
    private String firstName;
    private String lastName;
    private String department;
    private Date time;
    private Date startTime;
}
