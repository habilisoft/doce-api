package com.habilisoft.doce.api.dto.device;

import com.habilisoft.doce.api.domain.model.Device;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.modelmapper.ModelMapper;

/**
 * Created on 2019-04-21.
 */
@Data
public class RegisterDevice extends QueueEvent {
    @JsonProperty(value = "sn")
    private String serialNumber;
    @JsonProperty(value = "devinfo")
    private DeviceInfo deviceInfo;
    public Device toDevice(){
        Device device = new ModelMapper().map(this, Device.class);
        device.setSerialNumber(serialNumber);
        device.setConnected(true);
        return device;
    }
}
