package com.habilisoft.doce.api.domain.repositories;

import com.habilisoft.doce.api.domain.model.EmployeeDeviceData;
import com.habilisoft.doce.api.persistence.entities.EmployeeBiometricDataResponse;

import java.util.List;

/**
 * Created on 14/1/23.
 */
public interface EmployeeDeviceDataRepository {
    EmployeeDeviceData save(EmployeeDeviceData data);

    Boolean hasFingerPrint(Long employeeId);

    List<EmployeeBiometricDataResponse> getEmployeeBiometricData(Long employeeId);
}
