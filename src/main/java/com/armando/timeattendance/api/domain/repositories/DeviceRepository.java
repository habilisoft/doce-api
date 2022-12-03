package com.armando.timeattendance.api.domain.repositories;

import com.armando.timeattendance.api.domain.model.Device;
import com.armando.timeattendance.api.domain.model.Location;

import java.util.Optional;

/**
 * Created on 8/22/22.
 */
public interface DeviceRepository {
    Optional<Device> getBySerialNumber(String serialNumber);
    Device save(Device current);
    Optional<Device> findByLocation(Location location);
    void disconnect(String serialNumber);
}
