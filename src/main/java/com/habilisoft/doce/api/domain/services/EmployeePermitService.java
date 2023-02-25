package com.habilisoft.doce.api.domain.services;

import com.habilisoft.doce.api.domain.model.EmployeePermit;
import com.habilisoft.doce.api.domain.repositories.PermitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created on 18/2/23.
 */
@Service
@RequiredArgsConstructor
public class EmployeePermitService {
    private final PermitRepository permitRepository;

    public EmployeePermit createPermit(EmployeePermit permit) {
        return permitRepository.create(permit);
    }

    public EmployeePermit editPermit(EmployeePermit permit) {
        return permitRepository.edit(permit);
    }
}
