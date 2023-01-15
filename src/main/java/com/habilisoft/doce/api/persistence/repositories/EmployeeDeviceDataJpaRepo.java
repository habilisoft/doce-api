package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.persistence.entities.EmployeeDeviceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created on 14/1/23.
 */
public interface EmployeeDeviceDataJpaRepo extends JpaRepository<EmployeeDeviceDataEntity, Long> {
    Optional<EmployeeDeviceDataEntity> findByEmployeeIdAndNumberAndDeviceModel(Long employeeId, Integer number, String deviceModel);
}
