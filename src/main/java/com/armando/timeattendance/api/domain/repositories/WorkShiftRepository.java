package com.armando.timeattendance.api.domain.repositories;

import com.armando.timeattendance.api.domain.model.WorkShift;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface WorkShiftRepository {
    WorkShift save(WorkShift workShift);
    Optional<WorkShift> findByName(String name);
}
