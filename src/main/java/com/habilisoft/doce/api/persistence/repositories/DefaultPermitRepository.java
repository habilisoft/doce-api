package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.domain.model.EmployeePermit;
import com.habilisoft.doce.api.domain.repositories.PermitRepository;
import com.habilisoft.doce.api.persistence.entities.EmployeePermitEntity;
import com.habilisoft.doce.api.persistence.converters.EmployeePermitJpaConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Created on 19/2/23.
 */
@Repository
@RequiredArgsConstructor
public class DefaultPermitRepository implements PermitRepository {
    private final EmployeePermitJpaRepo jpaRepo;
    private final EmployeePermitJpaConverter converter;

    @Override
    public EmployeePermit findByEmployeeId(Long employeeId) {
        return null;
    }

    @Override
    public EmployeePermit create(EmployeePermit permit) {
        EmployeePermitEntity entity = converter.toJpaEntity(permit);
        entity = jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }

    @Override
    public EmployeePermit edit(EmployeePermit permit) {
        EmployeePermitEntity entity = converter.toJpaEntity(permit);
        entity = jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }

    @Override
    public void delete(Long permitId) {
        jpaRepo.deleteById(permitId);
    }
}
