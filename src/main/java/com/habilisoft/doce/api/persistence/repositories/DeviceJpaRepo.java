package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.persistence.entities.DeviceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface DeviceJpaRepo extends ExtendedRepository<DeviceEntity, Long> {
    Optional<DeviceEntity> findByLocationId(Long aLong);
    Optional<DeviceEntity> findBySerialNumber(String serialNumber);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE devices SET connected = false WHERE serial_number = :serialNumber")
    void disconnect(@Param("serialNumber") String serialNumber);
}
