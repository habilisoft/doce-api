package com.habilisoft.doce.api.web.employees.resources;

import com.habilisoft.doce.api.domain.exceptions.EmployeeNotFoundException;
import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.repositories.EmployeeRepository;
import com.habilisoft.doce.api.web.employees.converters.EmployeeEntityHttpConverter;
import com.habilisoft.doce.api.web.employees.dto.EmployeeHttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 30/11/22.
 */
@RestController
@RequiredArgsConstructor
public class EmployeeDetailsResource {
    private final EmployeeRepository employeeRepository;
    private final EmployeeEntityHttpConverter converter;

    @GetMapping("/employees/{employeeId}/details")
    public ResponseEntity<EmployeeHttpResponse> getEmployeeDetails(@PathVariable("employeeId") Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        EmployeeHttpResponse response = converter.toHttpResponse(employee);
        return ResponseEntity.ok(response);
    }
}
