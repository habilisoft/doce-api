package com.habilisoft.doce.api.domain.repositories;

import com.habilisoft.doce.api.domain.model.punch.policy.PunchPolicy;
import org.springframework.stereotype.Repository;

/**
 * Created on 25/12/22.
 */
public interface CompanySettingRepository {
    PunchPolicy getPunchPolicy();
}
