package com.habilisoft.doce.api.web.employees.converters;

import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.repositories.EmployeeDeviceDataRepository;
import com.habilisoft.doce.api.persistence.entities.EmployeeEntity;
import com.habilisoft.doce.api.web.employees.dto.EmployeeHttpResponse;
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
    private final EmployeeDeviceDataRepository deviceDataRepository;

    public EmployeeHttpResponse toHttpResponse(EmployeeEntity entity) {
        EmployeeHttpResponse response = modelMapper.map(entity, EmployeeHttpResponse.class);

        response.setHasFingerPrint(deviceDataRepository.hasFingerPrint(entity.getId()));

        return response;
    }

    public EmployeeHttpResponse toHttpResponse(Employee employee) {
        EmployeeHttpResponse response = modelMapper.map(employee, EmployeeHttpResponse.class);

        response.setHasFingerPrint(deviceDataRepository.hasFingerPrint(employee.getId()));

        return response;
    }
}
