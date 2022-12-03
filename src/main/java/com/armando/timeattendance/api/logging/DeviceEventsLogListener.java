package com.armando.timeattendance.api.logging;

import com.armando.timeattendance.api.domain.events.DeviceConnectedEvent;
import com.armando.timeattendance.api.domain.events.DeviceDisconnectedEvent;
import com.armando.timeattendance.api.domain.model.Device;
import com.armando.timeattendance.api.domain.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 25/11/22.
 */
@Slf4j
@Component
public class DeviceEventsLogListener {
    @Async
    @TransactionalEventListener
    public void onDeviceDisconnected(DeviceDisconnectedEvent event) {
        Device device = event.getDevice();
        Location location = Optional.of(device.getLocation()).orElse(new Location());
        String tenant = event.getTenant();
        log.warn(
                "Device disconnected {} {} {} {}",
                kv("deviceSerialNumber", device.getSerialNumber()),
                kv("tenant", tenant),
                kv("location", location.getName()),
                kv("eventName", LogEvents.DEVICE_DISCONNECTED)
        );
    }
    
    @Async
    @TransactionalEventListener
    public void onDeviceConnected(DeviceConnectedEvent event) {
        Device device = event.getDevice();
        Location location = Optional.of(device.getLocation()).orElse(new Location());
        String tenant = event.getTenant();
        log.warn(
                "Device Connected {} {} {} {}",
                kv("deviceSerialNumber", device.getSerialNumber()),
                kv("tenant", tenant),
                kv("location", location.getName()),
                kv("eventName", LogEvents.DEVICE_CONNECTED)
        );
    }
}
