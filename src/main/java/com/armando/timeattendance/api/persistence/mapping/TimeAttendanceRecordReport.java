package com.armando.timeattendance.api.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TimeAttendanceRecordReport {
    private Long id;
    private String firstName;
    private String lastName;
    private String department;
    private Date recordDate;
    private Date in;
    private Date startBreak;
    private Date endBreak;
    private Date out;
}
