package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.WorkShiftDetail;
import com.habilisoft.doce.api.persistence.entities.WorkShiftDetailEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 14/12/22.
 */
@Component
public class WorkShiftDetailJpaConverter implements JpaConverter<WorkShiftDetail, WorkShiftDetailEntity> {
    private final ModelMapper modelMapper;

    public WorkShiftDetailJpaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkShiftDetail fromJpaEntity(WorkShiftDetailEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map((j) -> modelMapper.map(j, WorkShiftDetail.class))
                .orElse(null);
    }

    @Override
    public WorkShiftDetailEntity toJpaEntity(WorkShiftDetail domainObject) {
        return Optional.ofNullable(domainObject)
                .map((j) -> modelMapper.map(j, WorkShiftDetailEntity.class))
                .orElse(null);
    }
}
