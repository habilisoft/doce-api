package com.armando.timeattendance.api.domain.events;

import com.armando.timeattendance.api.domain.model.Device;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

/**
 * Created on 25/11/22.
 */
@Data
public class DeviceDisconnectedEvent extends ApplicationEvent {
    private final Device device;
    private final String tenant;

    @Builder
    public DeviceDisconnectedEvent(final Object source,
                                   final Device device,
                                   final String tenant) {
        super(source);
        this.device = device;
        this.tenant = tenant;
    }
}
