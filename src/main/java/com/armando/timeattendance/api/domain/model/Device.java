package com.armando.timeattendance.api.domain.model;

import com.armando.timeattendance.api.dto.device.DeviceInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2019-04-21.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device{
    private String serialNumber;
    private String description;
    private DeviceInfo deviceInfo;
    private Location location;
    private Boolean active;
    private String sessionId;
    private Boolean connected;

    public Device(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void disconnect() {
        this.connected = false;
    }
}
