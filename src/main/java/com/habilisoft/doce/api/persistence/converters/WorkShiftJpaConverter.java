package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.persistence.entities.WorkShiftEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created on 28/11/22.
 */
@Component
@RequiredArgsConstructor
public class WorkShiftJpaConverter implements JpaConverter<WorkShift, WorkShiftEntity> {
    private final WorkShiftDetailJpaConverter detailJpaConverter;
    @Override
    public WorkShift fromJpaEntity(WorkShiftEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(j -> WorkShift.builder()
                        .id(j.getId())
                        .weekWorkHours(j.getWeekWorkHours())
                        .name(j.getName())
                        .details(
                                j.getDetails()
                                        .stream()
                                        .map(detailJpaConverter::fromJpaEntity)
                                        .collect(Collectors.toSet())
                        )
                        .lateGracePeriod(j.getLateGracePeriod())
                        .breakMinutes(j.getBreakMinutes())
                        .punchForBreak(j.getPunchForBreak())
                        .build()
                )
                .orElse(null);
    }

    @Override
    public WorkShiftEntity toJpaEntity(WorkShift domainObject) {
        if(domainObject == null) {
            return null;
        }

        WorkShiftEntity entity = Optional.ofNullable(domainObject)
                .map(j -> WorkShiftEntity.builder()
                        .id(j.getId())
                        .weekWorkHours(j.getWeekWorkHours())
                        .name(j.getName())
                        .lateGracePeriod(j.getLateGracePeriod())
                        .breakMinutes(j.getBreakMinutes())
                        .punchForBreak(j.getPunchForBreak())
                        .build()
                )
                .orElse(null);

        entity.setDetails(
                        domainObject.getDetails()
                                .stream()
                                .map(detailJpaConverter::toJpaEntity)
                                .collect(Collectors.toSet())

        );

        return entity;
    }
}
