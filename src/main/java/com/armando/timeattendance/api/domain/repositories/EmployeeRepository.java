package com.armando.timeattendance.api.domain.repositories;

import com.armando.timeattendance.api.domain.model.Employee;
import com.armando.timeattendance.api.domain.model.WorkShift;

import java.util.Optional;

/**
 * Created on 8/21/22.
 */
public interface EmployeeRepository {
    Optional<Employee> findById(Long id);
    WorkShift getEmployeeWorkShift(Long id);
    Optional<Employee> findByEnrollId(Integer enrollId);

    Optional<Employee> findByDocumentNumber(String documentNumber);
    Integer getNextEnrollId();

    Employee save(Employee employee);
}
