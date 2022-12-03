package com.armando.timeattendance.api.persistence.converters;

import com.armando.timeattendance.api.domain.model.Employee;
import com.armando.timeattendance.api.persistence.entities.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 8/26/22.
 */
@Component
@RequiredArgsConstructor
public class EmployeeJpaConverter implements JpaConverter<Employee, EmployeeEntity> {
    private final ModelMapper modelMapper;
    private final LocationJpaConverter locationJpaConverter;
    private final GroupJpaConverter groupJpaConverter;
    private final WorkShiftJpaConverter workShiftJpaConverter;

    @Override
    public EmployeeEntity toJpaEntity(Employee domainObject) {
        return Optional.ofNullable(domainObject)
                .map(d -> EmployeeEntity
                        .builder()
                        .id(d.getId())
                        .documentNumber(d.getDocumentNumber())
                        .enrollId(d.getEnrollId())
                        .externalId(d.getExternalId())
                        .fullName(d.getFullName())
                        .fingerprintData(d.getFingerprintData())
                        .locationType(d.getLocationType())
                        .workShift(workShiftJpaConverter.toJpaEntity(d.getWorkShift()))
                        .location(locationJpaConverter.toJpaEntity(d.getLocation()))
                        .group(groupJpaConverter.toJpaEntity(d.getGroup()))
                        .build())
                .orElse(null);
    }

    @Override
    public Employee fromJpaEntity(EmployeeEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(d -> Employee
                        .builder()
                        .id(d.getId())
                        .documentNumber(d.getDocumentNumber())
                        .enrollId(d.getEnrollId())
                        .externalId(d.getExternalId())
                        .fullName(d.getFullName())
                        .fingerprintData(d.getFingerprintData())
                        .locationType(d.getLocationType())
                        .workShift(workShiftJpaConverter.fromJpaEntity(d.getWorkShift()))
                        .location(locationJpaConverter.fromJpaEntity(d.getLocation()))
                        .group(groupJpaConverter.fromJpaEntity(d.getGroup()))
                        .build())
                .orElse(null);
    }
}
