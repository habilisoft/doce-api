package com.habilisoft.doce.api.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TimeAttendanceRecordReport {
    private Long id;
    private String fullName;
    private String group;
    private Date recordDate;
    private Date in;
    private Date startBreak;
    private Date endBreak;
    private Date out;
}
