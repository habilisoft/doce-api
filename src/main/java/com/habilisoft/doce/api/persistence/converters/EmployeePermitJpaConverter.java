package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.model.EmployeePermit;
import com.habilisoft.doce.api.persistence.entities.EmployeePermitEntity;
import com.habilisoft.doce.api.persistence.entities.BaseEmployee;
import com.habilisoft.doce.api.persistence.entities.PermitTypeEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Created on 19/2/23.
 */
@Component
@RequiredArgsConstructor
public class EmployeePermitJpaConverter implements JpaConverter<EmployeePermit, EmployeePermitEntity> {
    private final ModelMapper modelMapper;

    @Override
    public EmployeePermitEntity toJpaEntity(EmployeePermit domain) {
        return EmployeePermitEntity.builder()
                .fromDate(domain.getFromDate())
                .comment(domain.getComment())
                .toDate(domain.getToDate())
                .id(domain.getId())
                .permitType(
                        PermitTypeEntity.builder()
                                .id(domain.getPermitType().getId())
                                .build()
                )
                .employee(BaseEmployee.builder()
                                  .id(domain.getEmployee().getId())
                                  .build())
                .build();
    }

    @Override
    public EmployeePermit fromJpaEntity(EmployeePermitEntity entity) {
        return EmployeePermit.builder()
                .fromDate(entity.getFromDate())
                .comment(entity.getComment())
                .toDate(entity.getToDate())
                .permitType(
                        com.habilisoft.doce.api.domain.model.PermitType.builder()
                                .id(entity.getPermitType().getId())
                                .build()
                )
                .employee(Employee.builder()
                                  .id(entity.getEmployee().getId())
                                  .fullName(entity.getEmployee().getFullName())
                                  .enrollId(entity.getEmployee().getEnrollId())
                                  .build())
                .build();
    }


}
