package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.domain.model.Device;
import com.habilisoft.doce.api.domain.model.Location;
import com.habilisoft.doce.api.domain.repositories.DeviceRepository;
import com.habilisoft.doce.api.persistence.converters.DeviceJpaConverter;
import com.habilisoft.doce.api.persistence.entities.DeviceEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created on 8/22/22.
 */
@Repository
public class DefaultDeviceRepository implements DeviceRepository {
    private final DeviceJpaRepo jpaRepo;
    private final DeviceJpaConverter converter;

    public DefaultDeviceRepository(final DeviceJpaRepo jpaRepo,
                                   final DeviceJpaConverter converter) {
        this.jpaRepo = jpaRepo;
        this.converter = converter;
    }

    @Override
    public Optional<Device> getBySerialNumber(String serialNumber) {
        return jpaRepo.findBySerialNumber(serialNumber)
                .map(converter::fromJpaEntity);
    }

    @Override
    public Device save(Device device) {
        DeviceEntity entity = converter.toJpaEntity(device);
        entity = jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }

    @Override
    public Optional<Device> findByLocation(Location location) {
        return jpaRepo.findByLocationId(location.getId())
                .map(converter::fromJpaEntity);
    }

    @Transactional
    @Override
    public void disconnect(String serialNumber) {
        jpaRepo.disconnect(serialNumber);
    }
}
