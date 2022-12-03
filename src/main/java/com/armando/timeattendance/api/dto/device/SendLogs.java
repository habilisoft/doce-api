package com.armando.timeattendance.api.dto.device;

import lombok.Data;

import java.util.List;

/**
 * Created on 2019-04-21.
 */
@Data
public class SendLogs extends QueueEvent {
    List<LogRecord> record;

/*    @SerializedName("sn")
    @JsonProperty(value = "sn")
    private String serialNumber;*/

    private Integer count;
}
