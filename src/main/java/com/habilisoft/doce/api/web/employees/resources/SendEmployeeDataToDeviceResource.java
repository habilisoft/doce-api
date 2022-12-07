package com.habilisoft.doce.api.web.employees.resources;

import com.habilisoft.doce.api.domain.services.EmployeeDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 29/11/22.
 */
@RestController
@RequiredArgsConstructor
public class SendEmployeeDataToDeviceResource {
    private final EmployeeDeviceService employeeDeviceService;

    @PostMapping("/employees/{employeeId}/send-data")
    public ResponseEntity<?> sendEmployeeDataToDevice(@PathVariable Long employeeId) {
        employeeDeviceService.sendEmployeeDataToDevice(employeeId);
        return ResponseEntity.ok().build();
    }
}
