package com.habilisoft.doce.api.scheduler.model;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * Created on 3/12/22.
 */
@Data
public class ReportScheduleEntry {
    private LocalTime time;
    private List<DayOfWeek> days;
}
