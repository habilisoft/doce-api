package com.armando.timeattendance.api.persistence.repositories;

import com.armando.timeattendance.api.persistence.entities.TimeAttendanceRecordEntity;
import com.armando.timeattendance.api.persistence.mapping.AmbulatoryAssistanceDetailReport;
import com.armando.timeattendance.api.persistence.mapping.AmbulatoryAssistanceSummaryReport;
import com.armando.timeattendance.api.persistence.mapping.EmployeesWorkHourReport;
import com.armando.timeattendance.api.persistence.mapping.TimeAttendanceRecordReport;
import com.armando.timeattendance.api.persistence.mapping.TimeAttendanceSummaryReport;
import com.armando.timeattendance.api.persistence.mapping.TimeAttendanceSummaryReportDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created on 8/21/22.
 */
public interface TimeAttendanceRecordJpaRepository extends JpaRepository<TimeAttendanceRecordEntity, Long> {
    @Query("SELECT t from TimeAttendanceRecordEntity t inner join t.employee e " +
            "where e.id = :employeeId and t.recordDate = :recordDate " +
            "and t.device.serialNumber = :serialNumber order by t.time desc")
    Page<TimeAttendanceRecordEntity> findEmployeeEntries(@Param("employeeId") Long employeeId,
                                                   @Param("recordDate") Date recordDate,
                                                   @Param("serialNumber") String serialNumber,
                                                   Pageable pageable);

    @Query(name = "TimeAttendanceRecord.timeAttendanceReport", nativeQuery = true)
    Page<TimeAttendanceRecordReport> getTimeAttendanceReport(@Param("recordDateStart") Date recordDateStart,
                                                             @Param("recordDateEnd") Date recordDateEnd,
                                                             @Param("departmentId") Long departmentId,
                                                             @Param("employeeId") Long employeeId,
                                                             Pageable pageable);

    @Query(name = "TimeAttendanceRecord.timeAttendanceSummary", nativeQuery = true)
    List<TimeAttendanceSummaryReport> findTimeAttendanceSummary(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(name = "TimeAttendanceRecord.timeAttendanceSummaryDetails", nativeQuery = true)
    Page<TimeAttendanceSummaryReportDetail> findTimeAttendanceSummaryDetail(@Param("typeId") Integer typeId,
                                                                            @Param("recordDate") Date recordDate,
                                                                            Pageable pageable);

    @Query(name = "TimeAttendanceRecord.employeesWorkHoursByDateRange", nativeQuery = true)
    List<EmployeesWorkHourReport> findEmployeeWorkHoursByRange(@Param("filterType") String filterType,
                                                               @Param("filterDate") Date filterDate,
                                                               @Param("filterDateEnd") Date filterDateEnd,
                                                               @Param("departmentId") Long departmentId,
                                                               @Param("locationId") Long locationId,
                                                               @Param("employeeId") Long employeeId);

    @Query(name = "TimeAttendanceRecord.employeesWorkHoursDetailsResultsMapping", nativeQuery = true)
    Page<AmbulatoryAssistanceDetailReport> findEmployeeWorkHoursDetails(@Param("employeeId") Long employeeId,
                                                                          @Param("recordDate") Date filterDate,
                                                                          Pageable pageable);

    @Query(name = "TimeAttendanceRecord.ambulatoryAssistanceReportSummary", nativeQuery = true)
    List<AmbulatoryAssistanceSummaryReport> findAmbulatoryEmployeeAssistanceByRange(@Param("filterType") String filterType,
                                                                                    @Param("filterDate") Date filterDate,
                                                                                    @Param("filterDateEnd") Date filterDateEnd,
                                                                                    @Param("departmentId") Long departmentId,
                                                                                    @Param("employeeId") Long employeeId);


    @Query(name = "TimeAttendanceRecord.ambulatoryAssistanceReportDetail", nativeQuery = true)
    Page<AmbulatoryAssistanceDetailReport> findAmbulatoryEmployeeAssistanceDetails(@Param("employeeId") Long employeeId,
                                                                                   @Param("recordDate") Date filterDate,
                                                                                   Pageable pageable);
}
