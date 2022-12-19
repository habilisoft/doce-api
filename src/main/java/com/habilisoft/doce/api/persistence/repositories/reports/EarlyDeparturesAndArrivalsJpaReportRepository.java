package com.habilisoft.doce.api.persistence.repositories.reports;

import com.habilisoft.doce.api.persistence.entities.reports.EarlyDeparturesReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created on 18/12/22.
 */
public interface EarlyDeparturesAndArrivalsJpaReportRepository extends JpaRepository<EarlyDeparturesReportEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    e.ID AS employee_id,\n" +
            "    e.enroll_id,\n" +
            "    e.full_name as employee_name,\n" +
            "    G.NAME AS group_name ,\n" +
            "    w.name as workshift,\n" +
            "    t.time as record_time,\n" +
            "    t.record_date as record_date,\n" +
            "    t.difference_in_seconds\n" +
            "FROM\n" +
            "    time_attendance_records T\n" +
            "        INNER JOIN employees e ON T.employee_id = e.ID\n" +
            "        LEFT JOIN groups G ON G.ID = e.group_id\n" +
            "        left join work_shifts w on w.id = e.work_shift_id\n" +
            "where\n" +
            "    t.is_early is true\n" +
            "  and (:dateString = '' or :dateString\\:\\:date = t.record_date)\n" +
            "  and (:groupId = '' or :groupId\\:\\:int = g.id)")
    Page<EarlyDepartureResponse> getEarlyDepartures(@Param("dateString") String dateString,
                                                    @Param("groupId") String groupId,
                                                    Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    e.ID AS employee_id,\n" +
            "    e.enroll_id,\n" +
            "    e.full_name as employee_name,\n" +
            "    G.NAME AS group_name ,\n" +
            "    w.name as workshift,\n" +
            "    t.time as record_time,\n" +
            "    t.record_date as record_date,\n" +
            "    t.difference_in_seconds\n" +
            "FROM\n" +
            "    time_attendance_records T\n" +
            "        INNER JOIN employees e ON T.employee_id = e.ID\n" +
            "        LEFT JOIN groups G ON G.ID = e.group_id\n" +
            "        left join work_shifts w on w.id = e.work_shift_id\n" +
            "where\n" +
            "    t.is_late is true\n" +
            "  and (:dateString = '' or :dateString\\:\\:date = t.record_date)\n" +
            "  and (:groupId = '' or :groupId\\:\\:int = g.id)")
    Page<LateArrivalResponse> getLateArrivals(@Param("dateString") String dateString,
                                              @Param("groupId") String groupId,
                                              Pageable pageable);
}
