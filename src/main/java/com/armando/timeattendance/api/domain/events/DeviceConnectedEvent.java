package com.armando.timeattendance.api.domain.events;

import com.armando.timeattendance.api.domain.model.Device;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Created on 25/11/22.
 */
@Data
public class DeviceConnectedEvent extends ApplicationEvent {
    private final Device device;
    private final String tenant;

    @Builder
    public DeviceConnectedEvent(final Object source,
                                final String tenant,
                                final Device device) {
        super(source);
        this.device = device;
        this.tenant = tenant;
    }
}
