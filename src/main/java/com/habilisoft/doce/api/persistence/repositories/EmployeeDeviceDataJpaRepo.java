package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.persistence.entities.EmployeeDeviceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created on 14/1/23.
 */
public interface EmployeeDeviceDataJpaRepo extends JpaRepository<EmployeeDeviceDataEntity, Long> {
    Optional<EmployeeDeviceDataEntity> findByEmployeeIdAndNumberAndDeviceModel(Long employeeId, Integer number, String deviceModel);

    @Query(nativeQuery = true, value = "select exists (select employee_id FROM employee_device_data where employee_id = :employeeId)")
    Boolean employeeHasFingerPrintRecord(@Param("employeeId") Long employeeId);
}
