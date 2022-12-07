package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.domain.repositories.WorkShiftRepository;
import com.habilisoft.doce.api.persistence.converters.WorkShiftJpaConverter;
import com.habilisoft.doce.api.persistence.entities.WorkShiftEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
@Repository
@RequiredArgsConstructor
public class DefaultWorkShiftRepository implements WorkShiftRepository {
    private final WorkShiftJpaRepo jpaRepo;
    private final WorkShiftJpaConverter converter;

    @Override
    public WorkShift save(WorkShift workShift) {
        WorkShiftEntity entity = converter.toJpaEntity(workShift);
        jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }
    @Override
    public Optional<WorkShift> findByName(String name) {
        return jpaRepo.findByNameIgnoreCase(name)
                .map(converter::fromJpaEntity);
    }
}
