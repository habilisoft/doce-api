package com.armando.timeattendance.api.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created on 2019-05-07.
 */
@Data
public class DeleteUser extends QueueEvent {
    @JsonProperty(value = "enrollid")
    private Integer enrollId;
    private String deviceId;

    @Builder
    public DeleteUser(Integer enrollId, String deviceId) {
        this.enrollId = enrollId;
        this.type = Type.DELETE_USER;
    }
}
