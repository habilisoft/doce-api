package com.habilisoft.doce.api.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2019-05-07.
 */
@Data
@NoArgsConstructor
public class SendUserDataToDevice extends QueueEvent {
    @JsonProperty(value = "enrollid")
    private Integer enrollId;
    private String name;
    @JsonProperty(value = "backupnum")
    private Integer backupNum;
    private Integer admin;
    private Object record;
    private String deviceId;
    private String deviceModel;

    @Builder
    public SendUserDataToDevice(final Integer enrollId,
                                final String name,
                                final Integer backupNum,
                                final Integer admin,
                                final Object record,
                                final String deviceModel,
                                final String deviceId) {
        this.enrollId = enrollId;
        this.name = name;
        this.backupNum = backupNum;
        this.admin = admin;
        this.record = record;
        this.deviceId = deviceId;
        this.deviceModel = deviceModel;
        this.type = Type.SEND_USER_DATA_TO_DEVICE;
    }
}
