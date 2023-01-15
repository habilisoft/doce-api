package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.EmployeeDeviceData;
import com.habilisoft.doce.api.persistence.entities.EmployeeDeviceDataEntity;
import com.habilisoft.doce.api.persistence.entities.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 14/1/23.
 */
@Component
@RequiredArgsConstructor
public class EmployeeDeviceDataJpaConverter implements JpaConverter<EmployeeDeviceData, EmployeeDeviceDataEntity> {
    private final ModelMapper modelMapper;

    @Override
    public EmployeeDeviceData fromJpaEntity(EmployeeDeviceDataEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(j -> modelMapper.map(j, EmployeeDeviceData.class))
                .orElse(null);
    }

    @Override
    public EmployeeDeviceDataEntity toJpaEntity(EmployeeDeviceData domainObject) {
        EmployeeDeviceDataEntity entity = Optional.ofNullable(domainObject)
                .map(j -> modelMapper.map(j, EmployeeDeviceDataEntity.class))
                .orElse(null);
        if (entity != null) {
            entity.setEmployee(EmployeeEntity.builder().id(domainObject.getEmployee().getId()).build());
        }

        return entity;
    }
}
