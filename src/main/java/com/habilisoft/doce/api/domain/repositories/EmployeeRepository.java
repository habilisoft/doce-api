package com.habilisoft.doce.api.domain.repositories;

import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.model.WorkShift;

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
