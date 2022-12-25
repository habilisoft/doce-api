package com.habilisoft.doce.api.domain.model.punch.policy;

import lombok.Builder;
import lombok.Data;

/**
 * Created on 25/12/22.
 */
@Builder
@Data
public class LastPunchIsOutPunchPolicy implements PunchPolicy {
    private PunchPolicyType type;
    @Override
    public PunchPolicyType getType() {
        return PunchPolicyType.LAST_PUNCH_IS_OUT;
    }
}

