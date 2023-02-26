package com.habilisoft.doce.api.domain.repositories;

import com.habilisoft.doce.api.domain.model.EmployeePermit;

/**
 * Created on 18/2/23.
 */
public interface PermitRepository {
    EmployeePermit findByEmployeeId(Long employeeId);
    EmployeePermit create(EmployeePermit employeePlanning);

    EmployeePermit edit(EmployeePermit permit);

    void delete(Long permitId);
}
