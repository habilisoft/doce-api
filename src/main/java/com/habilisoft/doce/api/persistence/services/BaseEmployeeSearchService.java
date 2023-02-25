package com.habilisoft.doce.api.persistence.services;

import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.entities.BaseEmployee;
import com.habilisoft.doce.api.persistence.repositories.BaseEmployeeSearchRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 19/2/23.
 */
@Service
public class BaseEmployeeSearchService extends BaseService<BaseEmployee, Long> {
    private final BaseEmployeeSearchRepository baseEmployeeSearchRepository;

    public BaseEmployeeSearchService(BaseEmployeeSearchRepository baseEmployeeSearchRepository) {
        super(BaseEmployee.class);
        this.baseEmployeeSearchRepository = baseEmployeeSearchRepository;
    }

    public BaseEmployeeSearchRepository getRepository() {
        return baseEmployeeSearchRepository;
    }
}
