package com.habilisoft.doce.api.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TimeAttendanceSummaryReport {
    private Date recordDate;
    private Long total;
    private Long onTime;
    private Long late;
    private Long noPunch;
}
