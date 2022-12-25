package com.habilisoft.doce.api.domain.model;

import com.habilisoft.doce.api.domain.model.punch.PunchType;
import com.habilisoft.doce.api.dto.device.EventType;
import com.habilisoft.doce.api.dto.device.InOut;
import com.habilisoft.doce.api.dto.device.LogRecordMode;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TimeAttendanceRecord {
    private Long id;
    private Date recordDate;
    private Date time;
    private InOut inOut;
    private LogRecordMode mode;
    private EventType event;
    private PunchType punchType;
    private Device device;
    private Employee employee;
    private WorkShift workShift;
    private Boolean isEarlyDeparture;
    private Boolean isLateArrival;
    private Long differenceInSeconds;
}
