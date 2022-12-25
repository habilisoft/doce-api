package com.habilisoft.doce.api.domain.model;

import com.habilisoft.doce.api.domain.model.punch.policy.PunchPolicy;
import com.habilisoft.doce.api.domain.model.punch.policy.PunchPolicyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created on 2019-04-27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {
    private Long id;
    private String name;
    private Float weekWorkHours;
    private Long lateGracePeriod;
    private Boolean punchForBreak;
    private Integer breakMinutes;
    private Set<WorkShiftDetail> details;
    private PunchPolicy punchPolicy;
    private LinkedHashMap<String, Object> punchPolicyData;

    public Boolean getPunchForBreak() {
        return BooleanUtils.isTrue(punchForBreak);
    }
}
