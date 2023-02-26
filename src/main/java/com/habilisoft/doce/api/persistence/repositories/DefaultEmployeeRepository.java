package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.domain.repositories.EmployeeRepository;
import com.habilisoft.doce.api.persistence.converters.EmployeeJpaConverter;
import com.habilisoft.doce.api.persistence.entities.EmployeeEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created on 8/21/22.
 */
@Repository
public class DefaultEmployeeRepository implements EmployeeRepository {
    private final EmployeeJpaRepo jpaRepo;
    private final EmployeeJpaConverter converter;

    public DefaultEmployeeRepository(final EmployeeJpaRepo jpaRepo,
                                     final EmployeeJpaConverter converter) {
        this.jpaRepo = jpaRepo;
        this.converter = converter;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return jpaRepo.findById(id)
                .map(converter::fromJpaEntity);
    }

    @Override
    public WorkShift getEmployeeWorkShift(Long id) {
        return null;
    }

    @Override
    public Optional<Employee> findByEnrollId(Integer enrollId) {
        Optional<EmployeeEntity> entityOptional = jpaRepo.findByEnrollId(enrollId);

        if(entityOptional.isPresent()) {
            return entityOptional.map(converter::fromJpaEntity);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> findByDocumentNumber(String documentNumber) {
        return jpaRepo.findByDocumentNumber(documentNumber)
                .map(converter::fromJpaEntity);
    }

    @Override
    public Integer getNextEnrollId() {
        return jpaRepo.getNextEnrollId();
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeEntity entity = converter.toJpaEntity(employee);
        jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }

    @Override
    @Transactional
    public void disableEmployee(Long id) {
        jpaRepo.disableEmployee(id);
    }

    @Override
    @Transactional
    public void enableEmployee(Long id) {
        jpaRepo.enableEmployee(id);
    }
}
