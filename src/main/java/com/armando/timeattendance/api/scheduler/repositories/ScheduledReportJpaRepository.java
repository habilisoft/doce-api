package com.armando.timeattendance.api.scheduler.repositories;

import com.armando.timeattendance.api.scheduler.entitites.ScheduledReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 3/12/22.
 */
public interface ScheduledReportJpaRepository extends JpaRepository<ScheduledReportEntity, Long> {
}
