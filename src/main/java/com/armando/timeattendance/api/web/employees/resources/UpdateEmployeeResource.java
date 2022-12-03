package com.armando.timeattendance.api.web.employees.resources;

import com.armando.timeattendance.api.domain.commands.CreateEmployee;
import com.armando.timeattendance.api.domain.commands.UpdateEmployee;
import com.armando.timeattendance.api.domain.model.Employee;
import com.armando.timeattendance.api.domain.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 11/11/22.
 */
@RestController
public class UpdateEmployeeResource {
    private final EmployeeService employeeService;

    public UpdateEmployeeResource(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PutMapping("/employees/{employeeId}")
    ResponseEntity<Employee> createEmployee(@PathVariable("employeeId") Long employeeId,
                                            @RequestBody UpdateEmployee request) {
        Employee employee = employeeService.update(employeeId, request);
        return ResponseEntity.ok(employee);
    }
}
