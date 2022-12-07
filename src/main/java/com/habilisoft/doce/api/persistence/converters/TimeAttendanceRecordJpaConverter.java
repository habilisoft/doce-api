package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.TimeAttendanceRecord;
import com.habilisoft.doce.api.persistence.entities.TimeAttendanceRecordEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Created on 8/22/22.
 */
@Component
public class TimeAttendanceRecordJpaConverter implements JpaConverter<TimeAttendanceRecord, TimeAttendanceRecordEntity> {
    private final ModelMapper  modelMapper;

    public TimeAttendanceRecordJpaConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TimeAttendanceRecord fromJpaEntity(TimeAttendanceRecordEntity jpaEntity) {
        return modelMapper.map(jpaEntity, TimeAttendanceRecord.class);
    }

    @Override
    public TimeAttendanceRecordEntity toJpaEntity(TimeAttendanceRecord domainObject) {
        return modelMapper.map(domainObject, TimeAttendanceRecordEntity.class);
    }
}
