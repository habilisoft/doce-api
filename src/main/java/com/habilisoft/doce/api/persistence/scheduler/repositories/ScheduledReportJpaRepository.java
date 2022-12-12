package com.habilisoft.doce.api.persistence.scheduler.repositories;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.persistence.scheduler.entitites.ScheduledReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 3/12/22.
 */
public interface ScheduledReportJpaRepository extends ExtendedRepository<ScheduledReportEntity, Long> {
}
