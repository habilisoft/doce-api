package com.armando.timeattendance.api.persistence.repositories;

import com.armando.timeattendance.api.domain.repositories.ClientDeviceRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 8/25/22.
 */
@Repository
public class DefaultClientServiceRepository implements ClientDeviceRepository {
    private final ClientDeviceJpaRepository jpaRepo;

    public DefaultClientServiceRepository(final ClientDeviceJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public String getClientTenantByDeviceSerialNumber(String deviceSerialNumber) {
        return jpaRepo.getClientSubDomainByDeviceSerialNumber(deviceSerialNumber);
    }
}
