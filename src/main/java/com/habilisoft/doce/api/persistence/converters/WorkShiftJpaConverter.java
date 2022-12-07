package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.persistence.entities.WorkShiftEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 28/11/22.
 */
@Component
@RequiredArgsConstructor
public class WorkShiftJpaConverter implements JpaConverter<WorkShift, WorkShiftEntity> {
    @Override
    public WorkShift fromJpaEntity(WorkShiftEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(j -> WorkShift.builder()
                        .id(j.getId())
                        .weekWorkHours(j.getWeekWorkHours())
                        .name(j.getName())
                        .details(j.getDetails())
                        .lateGracePeriod(j.getLateGracePeriod())
                        .breakMinutes(j.getBreakMinutes())
                        .punchForBreak(j.getPunchForBreak())
                        .build()
                )
                .orElse(null);
    }

    @Override
    public WorkShiftEntity toJpaEntity(WorkShift domainObject) {
        return Optional.ofNullable(domainObject)
                .map(j -> WorkShiftEntity.builder()
                        .id(j.getId())
                        .weekWorkHours(j.getWeekWorkHours())
                        .name(j.getName())
                        .details(j.getDetails())
                        .lateGracePeriod(j.getLateGracePeriod())
                        .breakMinutes(j.getBreakMinutes())
                        .punchForBreak(j.getPunchForBreak())
                        .build()
                )
                .orElse(null);
    }
}
