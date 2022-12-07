package com.habilisoft.doce.api.domain.repositories;

import com.habilisoft.doce.api.domain.model.WorkShift;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface WorkShiftRepository {
    WorkShift save(WorkShift workShift);
    Optional<WorkShift> findByName(String name);
}
