package com.armando.timeattendance.api.domain.services;

import com.armando.timeattendance.api.domain.model.Employee;
import com.armando.timeattendance.api.domain.model.Group;
import com.armando.timeattendance.api.domain.model.ImportEmployeeRequest;
import com.armando.timeattendance.api.domain.model.Location;
import com.armando.timeattendance.api.domain.model.WorkShift;
import com.armando.timeattendance.api.domain.repositories.EmployeeRepository;
import com.armando.timeattendance.api.domain.repositories.GroupRepository;
import com.armando.timeattendance.api.domain.repositories.LocationRepository;
import com.armando.timeattendance.api.domain.repositories.WorkShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 28/11/22.
 */
@Service
@RequiredArgsConstructor
public class EmployeeImporter {
    private final EmployeeRepository employeeRepository;
    private final GroupRepository groupRepository;
    private final LocationRepository locationRepository;
    private final WorkShiftRepository workShiftRepository;

    @Transactional
    public List<Employee> importEmployees(List<ImportEmployeeRequest> employeeRequestList) {

        return employeeRequestList.stream()
                .map(this::createOrUpdateEmployee)
                .collect(Collectors.toList());
    }

    public Employee createOrUpdateEmployee(ImportEmployeeRequest req) {
        Employee employee = employeeRepository.findByDocumentNumber(req.getDocumentNumber())
                .orElse(Employee.builder()
                        .enrollId(employeeRepository.getNextEnrollId())
                        .documentNumber(req.getDocumentNumber())
                        .build());

        employee.setFullName(req.getName());
        employee.setGroup(getOrCreateGroup(req.getGroupName()));
        employee.setLocation(getOrCreateLocation(req.getLocationName()));
        employee.setWorkShift(getOrCreateWorkShift(req.getWorkShiftName()));
        employee.setExternalId(req.getExternalId());

        return employeeRepository.save(employee);
    }

    private Group getOrCreateGroup(String name) {
        return groupRepository.findByName(name)
                .orElseGet(() -> groupRepository.save(
                        Group.builder()
                                .name(name)
                                .build())
                );
    }

    private Location getOrCreateLocation(String name) {
        return locationRepository.findByName(name)
                .orElseGet(() -> locationRepository.save(
                        Location.builder()
                                .name(name)
                                .build())
                );
    }

    private WorkShift getOrCreateWorkShift(String name) {
        return workShiftRepository.findByName(name)
                .orElseGet(() -> workShiftRepository.save(
                        WorkShift.builder()
                                .name(name)
                                .build())
                );
    }
}
