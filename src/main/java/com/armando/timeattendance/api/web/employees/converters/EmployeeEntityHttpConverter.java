package com.armando.timeattendance.api.web.employees.converters;

import com.armando.timeattendance.api.domain.model.Employee;
import com.armando.timeattendance.api.persistence.entities.EmployeeEntity;
import com.armando.timeattendance.api.web.employees.dto.EmployeeHttpResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created on 29/11/22.
 */
@Component
@RequiredArgsConstructor
public class EmployeeEntityHttpConverter {
    private final ModelMapper modelMapper;

    public EmployeeHttpResponse toHttpResponse(EmployeeEntity entity) {
        EmployeeHttpResponse response = modelMapper.map(entity, EmployeeHttpResponse.class);

        response.setHasFingerPrint(StringUtils.hasLength(entity.getFingerprintData()));

        return response;
    }

    public EmployeeHttpResponse toHttpResponse(Employee employee) {
        EmployeeHttpResponse response = modelMapper.map(employee, EmployeeHttpResponse.class);

        response.setHasFingerPrint(StringUtils.hasLength(employee.getFingerprintData()));

        return response;
    }
}
