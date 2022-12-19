package com.habilisoft.doce.api.dto.device;

import com.habilisoft.doce.api.domain.model.Device;
import com.habilisoft.doce.api.domain.model.TimeAttendanceRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created on 2019-04-21.
 */
@Data
public class LogRecord {
    @SerializedName("enrollid")
    @JsonProperty(value = "enrollid")
    private Integer enrollId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date time;

    @SerializedName("inout")
    @JsonProperty(value = "inout")
    private InOut inOut;

    private LogRecordMode mode;

    private EventType event;

    private TimeAttendanceRecord toTimeAttendanceRecord(){
        TimeAttendanceRecord record = TimeAttendanceRecord.builder().build();
        BeanUtils.copyProperties(this, record);
        return record;
    }

    public TimeAttendanceRecord toTimeAttendanceRecord(String deviceSerialNumber){
        TimeAttendanceRecord record = toTimeAttendanceRecord();
        record.setDevice(new Device(deviceSerialNumber));
        return record;
    }
}
