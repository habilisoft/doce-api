package com.habilisoft.doce.api.persistence.entities;

import com.habilisoft.doce.api.domain.model.punch.PunchType;
import com.habilisoft.doce.api.dto.device.EventType;
import com.habilisoft.doce.api.dto.device.InOut;
import com.habilisoft.doce.api.dto.device.LogRecordMode;
import com.habilisoft.doce.api.persistence.BaseEntity;
import com.habilisoft.doce.api.persistence.mapping.AmbulatoryAssistanceDetailReport;
import com.habilisoft.doce.api.persistence.mapping.AmbulatoryAssistanceSummaryReport;
import com.habilisoft.doce.api.persistence.mapping.EmployeesWorkHourReport;
import com.habilisoft.doce.api.persistence.mapping.TimeAttendanceRecordReport;
import com.habilisoft.doce.api.persistence.mapping.TimeAttendanceSummaryReport;
import com.habilisoft.doce.api.persistence.mapping.TimeAttendanceSummaryReportDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created on 2019-04-21.
 */
@Data
@Entity
@Table(name = "time_attendance_records")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQueries(
        {
                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.timeAttendanceReport.count",
                        query = "SELECT count(1) as count\n" +
                                "from\n" +
                                "    time_attendance_records t\n" +
                                "    inner JOIN employees e on (e.id = t.employee_id)\n" +
                                "    inner join groups d on (d.id = e.group_id)\n" +
                                "    \n" +
                                "WHERE\n" +
                                "    t.record_date between :recordDateStart and :recordDateEnd\n" +
                                "    and (:employeeId = 0 or e.id = :employeeId)\n" +
                                "    and (:groupId = 0 or d.id = :groupId)\n" ,
                        resultSetMapping = "SqlResultSetMapping.count"
                ),
                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.timeAttendanceReport",
                        query = "SELECT\n" +
                                "    e.id,\n" +
                                "    e.full_name,\n" +
                                "    d.\"name\" as group_name,\n" +
                                "    t.record_date,\n" +
                                "    min(cast(t.time as time)) filter(where t.punch_type = 'IN') as in,\n" +
                                "    min(cast(t.time as time)) filter (where t.punch_type = 'START_BREAK') as start_break,\n" +
                                "    min(cast(t.time as time)) filter (where t.punch_type = 'END_BREAK') as end_break,\n" +
                                "    max(cast(t.time as time)) filter(where t.punch_type = 'OUT') as out\n" +
                                "from\n" +
                                "    time_attendance_records t\n" +
                                "    inner JOIN employees e on (e.id = t.employee_id)\n" +
                                "    inner join groups d on (d.id = e.group_id)\n" +
                                "    \n" +
                                "WHERE\n" +
                                "    t.record_date between :recordDateStart and :recordDateEnd\n" +
                                "    and (:employeeId = 0 or e.id = :employeeId)\n" +
                                "    and (:groupId = 0 or d.id = :groupId)\n" +
                                "group by e.id, full_name, group_name, record_date",
                        resultSetMapping = "timeAttendanceReportResultsMapping"
                ),
                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.timeAttendanceSummary",
                        query = "SELECT\n" +
                                "    d.record_date,\n" +
                                "    count(1) AS total,\n" +
                                "    count(1) FILTER (\n" +
                                "                    WHERE t.time BETWEEN wd.start_time - cast('15 minutes' AS INTERVAL)\n" +
                                "                    AND wd.start_time + cast('15 minutes' AS INTERVAL)\n" +
                                "            ) AS on_time,\n" +
                                "    count(1) FILTER (WHERE t.time > wd.start_time + cast('15 minutes' AS INTERVAL)) AS late,\n" +
                                "    count(1) FILTER (WHERE t.time IS NULL) AS no_punch\n" +
                                "FROM\n" +
                                "    generate_series(cast(:startDate as date), cast(:endDate as date), cast('1 day' as INTERVAL)) as d(record_date)\n" +
                                "    inner join employees_work_shifts_audit w \n" +
                                "    on (d.record_date BETWEEN w.start_date and case when w.end_date is null then now() else w.end_date end)\n" +
                                "    INNER JOIN work_shift_details wd \n" +
                                "    ON (wd.work_shift_id = w.work_shift_id and wd.day_of_week = upper(to_char(d.record_date, 'FMday'))\n" +
                                "        and \n" +
                                "            case \n" +
                                "                when cast(d.record_date as date) = cast(timezone('America/Santo_Domingo', now()) as date) then wd.start_time <= timezone('America/Santo_Domingo', CURRENT_TIME)\n" +
                                "                else TRUE\n" +
                                "            end) \n" +
                                "    INNER JOIN employees e ON (e.id = w.employee_id)\n" +
                                "    LEFT JOIN LATERAL \n" +
                                "    (\n" +
                                "        SELECT\n" +
                                "            t.employee_id,\n" +
                                "            cast(min(t.time) as time) AS time\n" +
                                "        FROM\n" +
                                "            time_attendance_records t\n" +
                                "        WHERE\n" +
                                "            t.employee_id = e.id\n" +
                                "            AND t.record_date = d.record_date\n" +
                                "        GROUP BY\n" +
                                "            employee_id\n" +
                                "    ) t ON TRUE\n" +
                                "group by d.record_date",
                        resultSetMapping = "timeAttendanceSummaryResultsMapping"
                ),
                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.timeAttendanceSummaryDetails.count",
                        query = "SELECT\n" +
                                "    count(1)\n" +
                                "FROM\n" +
                                "   generate_series(cast(:recordDate as date), cast(:recordDate as date), cast('1 day' as INTERVAL)) as d(record_date)\n" +
                                "    inner join employees_work_shifts_audit w \n" +
                                "    on (d.record_date BETWEEN w.start_date and case when w.end_date is null then now() else w.end_date end)\n" +
                                "    INNER JOIN work_shift_details wd \n" +
                                "    ON (\n" +
                                "       wd.work_shift_id = w.work_shift_id and wd.day_of_week = upper(to_char(d.record_date, 'FMday'))\n" +
                                "       and case\n" +
                                "               when cast(d.record_date as date) = cast(timezone('America/Santo_Domingo', now()) as date) then wd.start_time <= timezone('America/Santo_Domingo', CURRENT_TIME)\n" +
                                "               else true\n" +
                                "           end\n" +
                                ")\n" +
                                "    INNER JOIN employees e ON (e.id = w.employee_id)    \n" +
                                "    LEFT JOIN LATERAL \n" +
                                "    (\n" +
                                "        SELECT\n" +
                                "            t.employee_id,\n" +
                                "            cast(min(t.time) as time) AS time\n" +
                                "        FROM\n" +
                                "            time_attendance_records t\n" +
                                "        WHERE\n" +
                                "            t.employee_id = e.id\n" +
                                "            AND t.record_date = d.record_date\n" +
                                "        GROUP BY\n" +
                                "            employee_id\n" +
                                "    ) t ON TRUE\n" +
                                "WHERE case\n" +
                                "            when 0=:typeId then t.time BETWEEN wd.start_time - cast('15 minutes' AS INTERVAL) AND wd.start_time + cast('15 minutes' AS INTERVAL)\n" +
                                "            when 1=:typeId then t.time > wd.start_time + cast('15 minutes' AS INTERVAL)\n" +
                                "            when 2=:typeId then t.time IS NULL\n" +
                                "        end",
                        resultSetMapping = "SqlResultSetMapping.count"
                ),
                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.timeAttendanceSummaryDetails",
                        query = "SELECT\n" +
                                "    d.record_date,\n" +
                                "    e.id,\n" +
                                "    e.full_name,\n" +
                                "    de.name as group_name,\n" +
                                "    t.time,\n" +
                                "    wd.start_time \n" +
                                "FROM\n" +
                                "   generate_series(cast(:recordDate as date), cast(:recordDate as date), cast('1 day' as INTERVAL)) as d(record_date)\n" +
                                "    inner join employees_work_shifts_audit w \n" +
                                "    on (d.record_date BETWEEN w.start_date and case when w.end_date is null then now() else w.end_date end)\n" +
                                "    INNER JOIN work_shift_details wd \n" +
                                "    ON (\n" +
                                "       wd.work_shift_id = w.work_shift_id and wd.day_of_week = upper(to_char(d.record_date, 'FMday'))\n" +
                                "       and case\n" +
                                "               when cast(d.record_date as date) = cast(timezone('America/Santo_Domingo', now()) as date) then wd.start_time <= timezone('America/Santo_Domingo', CURRENT_TIME)\n" +
                                "               else true\n" +
                                "           end\n" +
                                ")\n" +
                                "    INNER JOIN employees e ON (e.id = w.employee_id)\n" +
                                "    INNER  JOIN groups de on (de.id = e.group_id)\n" +
                                "    LEFT JOIN LATERAL \n" +
                                "    (\n" +
                                "        SELECT\n" +
                                "            t.employee_id,\n" +
                                "            cast(min(t.time) as time) AS time\n" +
                                "        FROM\n" +
                                "            time_attendance_records t\n" +
                                "        WHERE\n" +
                                "            t.employee_id = e.id\n" +
                                "            AND t.record_date = d.record_date\n" +
                                "        GROUP BY\n" +
                                "            employee_id\n" +
                                "    ) t ON TRUE\n" +
                                "WHERE case\n" +
                                "            when 0=:typeId then t.time BETWEEN wd.start_time - cast('15 minutes' AS INTERVAL) AND wd.start_time + cast('15 minutes' AS INTERVAL)\n" +
                                "            when 1=:typeId then t.time > wd.start_time + cast('15 minutes' AS INTERVAL)\n" +
                                "            when 2=:typeId then t.time IS NULL\n" +
                                "        end",
                        resultSetMapping = "timeAttendanceSummaryDetailsResultsMapping"
                ),

                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.employeesWorkHoursByDateRange",
                        query = "with employee_dates as (\n" +
                                "     select \n" +
                                "         e.id as employee_id,\n" +
                                "         cast(rdate as date) as record_date\n" +
                                "     from\n" +
                                "         generate_series(\n" +
                                "                        CASE \n" +
                                "                            when :filterType = 'week' then date_trunc('week', cast(:filterDate as date)) \n" +
                                "                            when :filterType = 'month' then date_trunc('month', cast(:filterDate as date))\n" +
                                "                            when :filterType = 'range' then cast(:filterDate as date)\n" +
                                "                        END,\n" +
                                "                        CASE\n" +
                                "                            when :filterType = 'week' then date_trunc('week', cast(:filterDate as date)) + cast('6 day' as INTERVAL)  \n" +
                                "                            when :filterType = 'month' then date_trunc('month', cast(:filterDate as date)) + cast('1 month - 1 day' as INTERVAL)\n" +
                                "                            when :filterType = 'range' then cast(:filterDateEnd as date)\n" +
                                "                        END,\n" +
                                "                        cast('1 day' as INTERVAL)\n" +
                                "                       ) rdate,\n" +
                                "        employees e\n" +
                                " WHERE\n" +
                                "        (:groupId = 0 or e.group_id = :groupId)\n" +
                                "        and (:locationId = 0 or e.location_id = :locationId)\n" +
                                "        and (:employeeId = 0 or e.id = :employeeId)\n" +
                                "),\n" +
                                "\n" +
                                "time_attendance as (\n" +
                                "    select\n" +
                                "       e.employee_id,\n" +
                                "       t.time,\n" +
                                "       e.record_date,\n" +
                                "       (wd.end_time - wd.start_time) - (wd.break_end_time - wd.break_start_time) as work_shift_hours,\n" +
                                "       case when ROW_NUMBER() over(w) % 2 = 0 then 'out' else 'in' end as punch_type,\n" +
                                "       ROW_NUMBER() over(w) as row_number\n" +
                                "    from \n" +
                                "        employee_dates e\n" +
                                "        left join time_attendance_records t on (e.record_date = t.record_date and e.employee_id = t.employee_id)\n" +
                                "        left join work_shifts w on (t.work_shift_id = w.id)\n" +
                                "        left join work_shift_details wd on (wd.work_shift_id = w.id and wd.day_of_week = to_char(e.record_date, 'FMDAY'))\n" +
                                "    WINDOW w AS (PARTITION BY t.employee_id, t.record_date ORDER BY t.employee_id, t.record_date, t.time)\n" +
                                ")\n" +
                                ",\n" +
                                "\n" +
                                "time_attendance_work_hours as (\n" +
                                "    select \n" +
                                "        tin.employee_id,\n" +
                                "        tin.record_date,\n" +
                                "        tin.work_shift_hours,\n" +
                                "        sum(case when tout.punch_type = 'out' then tout.time - tin.time end) as work_time\n" +
                                "    from\n" +
                                "        time_attendance tin\n" +
                                "        left join time_attendance tout \n" +
                                "        on (tin.employee_id = tout.employee_id and tin.record_date = tout.record_date and tin.row_number = (tout.row_number -1))\n" +
                                "    group by tin.employee_id, tin.record_date, tin.work_shift_hours\n" +
                                ")\n" +
                                "\n" +
                                "select \n" +
                                "    e.id,\n" +
                                "    e.first_name,\n" +
                                "    e.last_name,\n" +
                                "    t.record_date,\n" +
                                "    t.work_time,\n" +
                                "case \n" +
                                "        when t.work_time BETWEEN t.work_shift_hours - cast('15 minutes' as INTERVAL) AND t.work_shift_hours + cast('15 minutes' as INTERVAL) then 'NORMAL'\n" +
                                "        when t.work_time > t.work_shift_hours + cast('15 minutes' as INTERVAL) then 'OVERTIME'\n" +
                                "        when t.work_time < t.work_shift_hours - cast('15 minutes' as INTERVAL) then 'UNDERTIME'\n" +
                                "        else ''\n" +
                                "    end as type_hours,\n" +
                                "    sum(coalesce(t.work_time, cast('00:00:00' as INTERVAL))) over(partition by e.id) as total_work_time\n" +
                                "from \n" +
                                "    time_attendance_work_hours t\n" +
                                "    inner join employees e on (e.id = t.employee_id)\n" +
                                "order by id, record_date\n",
                        resultSetMapping = "employeesWorkHoursByDateRangeResultsMapping"
                ),

                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.employeesWorkHoursDetailsResultsMapping.count",
                        query = "select \n" +
                                "    count(1)\n" +
                                "from \n" +
                                "    time_attendance_records r\n" +
                                "    inner join employees e on (r.employee_id = e.id)\n" +
                                "    inner join devices d on (d.serial_number = r.device_serial_number)\n" +
                                "WHERE\n" +
                                "    e.id = :employeeId\n" +
                                "    and r.record_date = :recordDate",
                        resultSetMapping = "SqlResultSetMapping.count"
                ),

                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.employeesWorkHoursDetailsResultsMapping",
                        query = "select \n" +
                                "    e.id,\n" +
                                "    e.first_name,\n" +
                                "    e.last_name,\n" +
                                "    l.name as location_name,\n" +
                                "    r.device_serial_number,\n" +
                                "    r.record_date,\n" +
                                "    r.time as time\n" +
                                "from \n" +
                                "    time_attendance_records r\n" +
                                "    inner join employees e on (r.employee_id = e.id)\n" +
                                "    inner join devices d on (d.serial_number = r.device_serial_number)\n" +
                                "    left join locations l on (l.id = d.location_id) \n" +
                                "WHERE\n" +
                                "    e.id = :employeeId\n" +
                                "    and r.record_date = :recordDate\n" +
                                "order by time",
                        resultSetMapping = "ambulatoryAssistanceReportDetailResultsMapping"
                ),

                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.ambulatoryAssistanceReportSummary",
                        query = "with employee_dates as (\n" +
                                "    SELECT\n" +
                                "        e.id,\n" +
                                "        e.first_name,\n" +
                                "        e.last_name,\n" +
                                "        cast(d.record_date as date) as record_date\n" +
                                "    from \n" +
                                "        generate_series(\n" +
                                "            case \n" +
                                "              when :filterType = 'week' then date_trunc('week', cast(:filterDate as date))\n" +
                                "              when :filterType = 'month' then date_trunc('month', cast(:filterDate as date))\n" +
                                "              when :filterType = 'range' then cast(:filterDate as date)\n" +
                                "            end,\n" +
                                "            case\n" +
                                "                when :filterType = 'week' then date_trunc('week', cast(:filterDate as date)) + cast('6 days' as INTERVAL)\n" +
                                "                when :filterType = 'month' then date_trunc('month', cast(:filterDate as date)) + cast('1 month - 1 day' as INTERVAL)\n" +
                                "                when :filterType = 'range' then cast(:filterDateEnd as date)\n" +
                                "            end,\n" +
                                "           cast('1 day' as INTERVAL)\n" +
                                "        ) as d(record_date),\n" +
                                "         employees e\n" +
                                "     WHERE\n" +
                                "         e.location_type = 'AMBULATORY'\n" +
                                "         and (:groupId = 0 or e.group_id = :groupId)\n" +
                                "         and (:employeeId = 0 or e.id = :employeeId)\n" +
                                ")\n" +
                                "\n" +
                                "select \n" +
                                "    e.id,\n" +
                                "    e.first_name,\n" +
                                "    e.last_name,\n" +
                                "    e.record_date,\n" +
                                "    count(DISTINCT r.device_serial_number) as visits\n" +
                                "FROM \n" +
                                "    employee_dates e\n" +
                                "    left join time_attendance_records r on (r.record_date = e.record_date and e.id = r.employee_id)\n" +
                                "group by e.id, e.first_name, e.last_name, e.record_date\n" +
                                "order by e.id",
                        resultSetMapping = "ambulatoryAssistanceReportSummaryResultsMapping"
                ),


                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.ambulatoryAssistanceReportDetail.count",
                        query = "select \n" +
                                "    count(DISTINCT r.device_serial_number)\n" +
                                "from \n" +
                                "    time_attendance_records r\n" +
                                "    inner join employees e on (r.employee_id = e.id)\n" +
                                "    inner join devices d on (d.serial_number = r.device_serial_number)\n" +
                                "WHERE\n" +
                                "    e.id = :employeeId\n" +
                                "    and r.record_date = :recordDate",
                        resultSetMapping = "SqlResultSetMapping.count"
                ),

                @NamedNativeQuery(
                        name = "TimeAttendanceRecord.ambulatoryAssistanceReportDetail",
                        query = "select \n" +
                                "    e.id,\n" +
                                "    e.first_name,\n" +
                                "    e.last_name,\n" +
                                "    l.name as location_name,\n" +
                                "    r.device_serial_number,\n" +
                                "    r.record_date,\n" +
                                "    min(r.time) as time\n" +
                                "from \n" +
                                "    time_attendance_records r\n" +
                                "    inner join employees e on (r.employee_id = e.id)\n" +
                                "    inner join devices d on (d.serial_number = r.device_serial_number)\n" +
                                "    left join locations l on (l.id = d.location_id) \n" +
                                "WHERE\n" +
                                "    e.id = :employeeId\n" +
                                "    and r.record_date = :recordDate\n" +
                                "group by e.id, e.first_name, e.last_name, location_name, r.device_serial_number, r.record_date\n" +
                                "order by time",
                        resultSetMapping = "ambulatoryAssistanceReportDetailResultsMapping"
                )
        }
)

@SqlResultSetMappings({
        @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "count")),
        @SqlResultSetMapping(
                name = "timeAttendanceReportResultsMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = TimeAttendanceRecordReport.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "full_name", type = String.class),
                                        @ColumnResult(name = "group_name", type = String.class),
                                        @ColumnResult(name = "record_date", type = Date.class),
                                        @ColumnResult(name = "in", type = Date.class),
                                        @ColumnResult(name = "start_break", type = Date.class),
                                        @ColumnResult(name = "end_break", type = Date.class),
                                        @ColumnResult(name = "out", type = Date.class),
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "timeAttendanceSummaryResultsMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = TimeAttendanceSummaryReport.class,
                                columns = {
                                        @ColumnResult(name = "record_date", type = Date.class),
                                        @ColumnResult(name = "total", type = Long.class),
                                        @ColumnResult(name = "on_time", type = Long.class),
                                        @ColumnResult(name = "late", type = Long.class),
                                        @ColumnResult(name = "no_punch", type = Long.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "employeesWorkHoursByDateRangeResultsMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = EmployeesWorkHourReport.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "first_name", type = String.class),
                                        @ColumnResult(name = "last_name", type = String.class),
                                        @ColumnResult(name = "record_date", type = Date.class),
                                        @ColumnResult(name = "work_time", type = Date.class),
                                        @ColumnResult(name = "type_hours", type = String.class),
                                        @ColumnResult(name = "total_work_time", type = String.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "timeAttendanceSummaryDetailsResultsMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = TimeAttendanceSummaryReportDetail.class,
                                columns = {
                                        @ColumnResult(name = "record_date", type = Date.class),
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "full_name", type = String.class),
                                        @ColumnResult(name = "group_name", type = String.class),
                                        @ColumnResult(name = "time", type = Date.class),
                                        @ColumnResult(name = "start_time", type = Date.class)

                                }
                        )
                }
        ),

        @SqlResultSetMapping(
                name = "ambulatoryAssistanceReportSummaryResultsMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = AmbulatoryAssistanceSummaryReport.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "first_name", type = String.class),
                                        @ColumnResult(name = "last_name", type = String.class),
                                        @ColumnResult(name = "record_date", type = Date.class),
                                        @ColumnResult(name = "visits", type = Long.class)

                                }
                        )
                }
        ),

        @SqlResultSetMapping(
                name = "ambulatoryAssistanceReportDetailResultsMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = AmbulatoryAssistanceDetailReport.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "first_name", type = String.class),
                                        @ColumnResult(name = "last_name", type = String.class),
                                        @ColumnResult(name = "location_name", type = String.class),
                                        @ColumnResult(name = "device_serial_number", type = String.class),
                                        @ColumnResult(name = "record_date", type = Date.class),
                                        @ColumnResult(name = "time", type = Date.class)

                                }
                        )
                }
        )

})

public class TimeAttendanceRecordEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "time_attendance_log_id_seq", sequenceName = "time_attendance_log_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_attendance_log_id_seq")
    private Long id;

    @Column(name = "record_date")
    @Temporal(TemporalType.DATE)
    private Date recordDate;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column(name = "in_out")
    @Enumerated(EnumType.STRING)
    private InOut inOut;

    @Column(name = "record_mode")
    @Enumerated(EnumType.STRING)
    private LogRecordMode mode;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType event;

    @Column(name = "punch_type")
    @Enumerated(EnumType.STRING)
    private PunchType punchType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_serial_number")
    private DeviceEntity device;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_shift_id")
    private WorkShiftEntity workShift;

    @Column(name = "is_early")
    private Boolean isEarlyDeparture;

    @Column(name = "is_late")
    private Boolean isLateArrival;

    @Column(name = "difference_in_seconds")
    private Long differenceInSeconds;

    @PrePersist
    private void prePersist() {
        this.recordDate = this.time;
    }
}
