package com.habilisoft.doce.api.domain.repositories;

/**
 * Created on 8/25/22.
 */
public interface ClientDeviceRepository {
    String getClientTenantByDeviceSerialNumber(String deviceSerialNumber);
}
