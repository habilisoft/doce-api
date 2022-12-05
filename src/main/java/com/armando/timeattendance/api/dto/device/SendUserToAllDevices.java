package com.armando.timeattendance.api.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2019-05-07.
 */
@Data
@NoArgsConstructor
public class SendUserToAllDevices extends QueueEvent {
    @JsonProperty(value = "enrollid")
    private Integer enrollId;
    private String name;
    @JsonProperty(value = "backupnum")
    private Integer backupNum;
    private Integer admin;
    private String record;
    private String clientId;

    @Builder
    public SendUserToAllDevices(Integer enrollId, String name, Integer backupNum, Integer admin, String record, String clientId) {
        this.enrollId = enrollId;
        this.name = name;
        this.backupNum = backupNum;
        this.admin = admin;
        this.record = record;
        this.clientId = clientId;
        this.type = Type.SEND_USER_TO_ALL_DEVICES;
    }
}