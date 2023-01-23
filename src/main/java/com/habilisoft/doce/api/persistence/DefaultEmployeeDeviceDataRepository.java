package com.habilisoft.doce.api.persistence;

import com.habilisoft.doce.api.domain.model.EmployeeDeviceData;
import com.habilisoft.doce.api.domain.repositories.EmployeeDeviceDataRepository;
import com.habilisoft.doce.api.persistence.converters.EmployeeDeviceDataJpaConverter;
import com.habilisoft.doce.api.persistence.entities.EmployeeDeviceDataEntity;
import com.habilisoft.doce.api.persistence.entities.EmployeeEntity;
import com.habilisoft.doce.api.persistence.repositories.EmployeeDeviceDataJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Created on 14/1/23.
 */
@Repository
@RequiredArgsConstructor
public class DefaultEmployeeDeviceDataRepository implements EmployeeDeviceDataRepository {
    private final EmployeeDeviceDataJpaRepo jpaRepo;
    private final EmployeeDeviceDataJpaConverter converter;

    @Override
    public EmployeeDeviceData save(EmployeeDeviceData data) {
        Integer number = data.getNumber();
        String deviceModel = data.getDeviceModel();
        Long employeeId = data.getEmployee().getId();
        EmployeeDeviceDataEntity entity =
                jpaRepo.findByEmployeeIdAndNumberAndDeviceModel(employeeId, number, deviceModel)
                        .orElse(EmployeeDeviceDataEntity.builder()
                                        .deviceModel(deviceModel)
                                        .employee(EmployeeEntity.builder().id(employeeId).build())
                                        .number(number)
                                        .build());
        entity.setRecord(data.getRecord());
        jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }

    @Override
    public Boolean hasFingerPrint(Long employeeId) {

        return jpaRepo.employeeHasFingerPrintRecord(employeeId);
    }
}
