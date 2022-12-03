package com.armando.timeattendance.api.domain.model;

import com.armando.timeattendance.api.serialization.CustomJsonTimeDeserializerWithoutTimeZone;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkShiftDetail {
    private Boolean selected;
    private WorkShiftDay weekDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkShiftDetail that = (WorkShiftDetail) o;
        return weekDay == that.weekDay;
    }

    @Override
    public int hashCode() {

        return Objects.hash(weekDay);
    }
}
