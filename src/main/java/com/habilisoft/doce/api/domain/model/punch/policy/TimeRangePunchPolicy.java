package com.habilisoft.doce.api.domain.model.punch.policy;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

/**
 * Created on 25/12/22.
 */
@Builder
@Data
public class TimeRangePunchPolicy implements PunchPolicy {
    private LocalTime inStart;
    private LocalTime inEnd;
    private PunchPolicyType type;

    public static TimeRangePunchPolicy get(Object punchPolicyMetaData) {
        return (TimeRangePunchPolicy) punchPolicyMetaData;
    }

    @Override
    public PunchPolicyType getType() {
        return PunchPolicyType.IN_TIME_RANGE;
    }
}
