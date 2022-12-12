package com.habilisoft.doce.api.domain.services;

import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.domain.model.ImportEmployeeRequest;
import com.habilisoft.doce.api.domain.model.Location;
import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.domain.repositories.EmployeeRepository;
import com.habilisoft.doce.api.domain.repositories.GroupRepository;
import com.habilisoft.doce.api.domain.repositories.LocationRepository;
import com.habilisoft.doce.api.domain.repositories.WorkShiftRepository;
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
        Employee employee = employeeRepository.findByEnrollId(req.getEnrollId())
                .orElse(Employee.builder()
                        .enrollId(req.getEnrollId())
                        .documentNumber(req.getDocumentNumber())
                        .build());

        employee.setFullName(req.getName());
        employee.setGroup(getOrCreateGroup(req.getGroupName()));
        employee.setLocation(getOrCreateLocation(req.getLocationName()));
        employee.setWorkShift(getOrCreateWorkShift(req.getWorkShiftName()));

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
