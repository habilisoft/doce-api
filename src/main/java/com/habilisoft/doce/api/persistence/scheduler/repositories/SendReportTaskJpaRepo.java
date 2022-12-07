package com.habilisoft.doce.api.persistence.scheduler.repositories;

import com.habilisoft.doce.api.persistence.scheduler.entitites.SendReportTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created on 7/12/22.
 */
public interface SendReportTaskJpaRepo extends JpaRepository<SendReportTaskEntity, Long> {
    List<SendReportTaskEntity> findAllByScheduledReportId(Long scheduledReportId);
    void deleteAllByScheduledReportId(Long scheduledReportId);

    @Modifying
    @Query(value = "delete from send_report_tasks where scheduled_report_id in (select id from scheduled_reports where deleted is true)", nativeQuery = true)
    void deleteByDeletedReports();
}
