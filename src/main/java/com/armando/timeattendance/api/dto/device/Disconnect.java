package com.armando.timeattendance.api.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2019-05-07.
 */
@Data
@NoArgsConstructor
public class Disconnect extends QueueEvent {
    @JsonProperty(value = "sn")
    private String serialNumber;

    public Disconnect(String serialNumber) {
        this.serialNumber = serialNumber;
        this.type = Type.DISCONNECT;
    }
}
