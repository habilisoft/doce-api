package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.domain.model.punch.policy.LastPunchIsOutPunchPolicy;
import com.habilisoft.doce.api.domain.model.punch.policy.PunchPolicy;
import com.habilisoft.doce.api.domain.model.punch.policy.PunchPolicyType;
import com.habilisoft.doce.api.domain.model.punch.policy.TimeRangePunchPolicy;
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
    private final PunchPolicyConverter policyConverter;

    @Override
    public WorkShift fromJpaEntity(WorkShiftEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(j -> WorkShift.builder()
                        .id(j.getId())
                        .weekWorkHours(j.getWeekWorkHours())
                        .name(j.getName())
                        .details(
                                Optional.ofNullable(j.getDetails())
                                        .map( d -> d.stream()
                                                .map(detailJpaConverter::fromJpaEntity)
                                                .collect(Collectors.toSet()))
                                        .orElse(null))
                        .lateGracePeriod(j.getLateGracePeriod())
                        .breakMinutes(j.getBreakMinutes())
                        .punchForBreak(j.getPunchForBreak())
                        .punchPolicy(policyConverter.getPunchPolicy(j.getPunchPolicyMetaData()))
                        .build()
                )
                .orElse(null);
    }

    @Override
    public WorkShiftEntity toJpaEntity(WorkShift j) {
        if (j == null) {
            return null;
        }

        WorkShiftEntity entity = WorkShiftEntity.builder()
                .id(j.getId())
                .weekWorkHours(j.getWeekWorkHours())
                .name(j.getName())
                .lateGracePeriod(j.getLateGracePeriod())
                .breakMinutes(j.getBreakMinutes())
                .punchForBreak(j.getPunchForBreak())
                .punchPolicyType(Optional.ofNullable(j.getPunchPolicy()).map(PunchPolicy::getType).orElse(PunchPolicyType.LAST_PUNCH_IS_OUT))
                .punchPolicyMetaData(policyConverter.getPunchPolicy(j.getPunchPolicy()))
                .build();

        entity.setDetails(
                Optional.ofNullable(j.getDetails())
                                .map( d -> d.stream()
                                        .map(detailJpaConverter::toJpaEntity)
                                        .collect(Collectors.toSet()))
                                        .orElse(null));

        return entity;
    }

    private PunchPolicy getPunchPolicy(WorkShiftEntity entity) {
        if(entity.getPunchPolicyType() == null) {
            return LastPunchIsOutPunchPolicy.builder()
                    .type(PunchPolicyType.LAST_PUNCH_IS_OUT)
                    .build();
        }
        return switch (entity.getPunchPolicyType()) {
            case LAST_PUNCH_IS_OUT -> LastPunchIsOutPunchPolicy.builder()
                    .type(PunchPolicyType.LAST_PUNCH_IS_OUT)
                    .build();
            default -> TimeRangePunchPolicy.get(entity.getPunchPolicyMetaData());
        };
    }
}
