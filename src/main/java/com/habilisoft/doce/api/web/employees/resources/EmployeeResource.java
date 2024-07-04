package com.habilisoft.doce.api.web.employees.resources;

import com.habilisoft.doce.api.domain.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 26/2/23.
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeResource {
    private final EmployeeService employeeService;

    @PostMapping("/{id}/disable")
    ResponseEntity disableEmployee(@PathVariable Long id) {
        employeeService.disableEmployee(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/enable")
    ResponseEntity enableEmployee(@PathVariable Long id) {
        employeeService.enableEmployee(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);

        return ResponseEntity.ok().build();
    }
}
