package com.armando.timeattendance.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;

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
    private Integer lateGracePeriod;
    private Boolean punchForBreak;
    private Integer breakMinutes;
    private Set<WorkShiftDetail> details;

    public Boolean getPunchForBreak() {
        return BooleanUtils.isTrue(punchForBreak);
    }
}
