package com.habilisoft.doce.api.web.employees.resources;

import com.habilisoft.doce.api.domain.repositories.EmployeeDeviceDataRepository;
import com.habilisoft.doce.api.persistence.entities.EmployeeBiometricDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 3/3/23.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees/{employeeId}/biometric-data")
public class EmployeeBiometricDataResource {
    private final EmployeeDeviceDataRepository employeeDeviceDataRepository;

    @GetMapping
    ResponseEntity<?> getEmployeeBiometricData(@PathVariable Long employeeId) {
        List<EmployeeBiometricDataResponse> response = employeeDeviceDataRepository
                .getEmployeeBiometricData(employeeId);
        return ResponseEntity.ok(response);
    }
}
