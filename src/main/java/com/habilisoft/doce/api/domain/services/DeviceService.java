package com.habilisoft.doce.api.domain.services;

import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.habilisoft.doce.api.domain.events.DeviceConnectedEvent;
import com.habilisoft.doce.api.domain.events.DeviceDisconnectedEvent;
import com.habilisoft.doce.api.domain.exceptions.DeviceNotFoundException;
import com.habilisoft.doce.api.domain.model.Device;
import com.habilisoft.doce.api.domain.model.Location;
import com.habilisoft.doce.api.domain.repositories.DeviceRepository;
import com.habilisoft.doce.api.dto.device.Disconnect;
import com.habilisoft.doce.api.dto.device.RegisterDevice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 2019-04-21.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final ApplicationEventPublisher publisher;
    private final EmployeeDeviceService employeeDeviceService;

    @Transactional
    public void registerDevice(RegisterDevice registerDevice) {
        try {
            Device device = deviceRepository.getBySerialNumber(registerDevice.getSerialNumber())
                    .orElse(registerDevice.toDevice());
            device.setDeviceInfo(registerDevice.getDeviceInfo());
            device.setConnected(true);
            device = deviceRepository.save(device);

            publisher.publishEvent(
                    DeviceConnectedEvent.builder()
                            .source(this)
                            .tenant(TenantContext.getCurrentTenant())
                            .device(device)
                            .build());

        } catch (Exception e) {
            log.error("Exception registering device {}", kv("exceptionMessage", e.getMessage()), e);
        }

    }

    public Device update(Device entity) {

        Device current = deviceRepository.getBySerialNumber(entity.getSerialNumber())
                .orElseThrow();

        current.setDescription(entity.getDescription());
        current.setLocation(entity.getLocation());
        current.setActive(entity.getActive());

        return deviceRepository.save(current);
    }

    public Device save(Device entity) {
        log.info("Finding device...");
        Device current = deviceRepository.getBySerialNumber(entity.getSerialNumber())
                .orElse(new Device());

        BeanUtils.copyProperties(entity, current, "active", "location", "description");

        log.info("Saving device...");
        return deviceRepository.save(current);
    }

    public Device getDevice(String deviceSerialNumber) {
        return deviceRepository.getBySerialNumber(deviceSerialNumber)
                .orElseThrow();
    }

    private Device getDevice(Location location) {
        return deviceRepository.findByLocation(location)
                .orElseThrow();
    }

    @Transactional
    public void disconnect(Disconnect disconnect) {
        deviceRepository.getBySerialNumber(disconnect.getSerialNumber())
                .ifPresent(device -> {
                    device.disconnect();
                    deviceRepository.save(device);
                    publisher.publishEvent(
                            DeviceDisconnectedEvent.builder()
                                    .source(this)
                                    .tenant(TenantContext.getCurrentTenant())
                                    .device(device)
                                    .build());
                });
    }

    @Transactional
    public void edit(String serialNumber, Device device) {
        deviceRepository.getBySerialNumber(serialNumber)
                .ifPresent(dev -> {
                    dev.setDescription(device.getDescription());
                    deviceRepository.save(dev);
                });
    }

    public void synch(String serialNumber) {
        Device device = deviceRepository.getBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException(serialNumber));
        employeeDeviceService.sendAllEmployeeDataDevice(device);
    }

    public void sendFp(String serialNumber) {
        Device device = deviceRepository.getBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException(serialNumber));
        employeeDeviceService.sendEmployeeFpDataToDevice(device);
    }
}
