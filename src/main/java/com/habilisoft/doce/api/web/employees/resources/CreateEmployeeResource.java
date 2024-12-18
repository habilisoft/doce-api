package com.habilisoft.doce.api.web.employees.resources;

import com.habilisoft.doce.api.domain.commands.CreateEmployee;
import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 11/11/22.
 */
@RestController
public class CreateEmployeeResource {
    private final EmployeeService employeeService;

    public CreateEmployeeResource(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployee request) {
        Employee employee = employeeService.create(request);
        return ResponseEntity.ok(employee);
    }
}
