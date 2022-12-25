package com.habilisoft.doce.api.domain.services;

import com.habilisoft.doce.api.domain.exceptions.EmployeeNotFoundException;
import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.model.TimeAttendanceRecord;
import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.domain.model.WorkShiftDetail;
import com.habilisoft.doce.api.domain.model.punch.policy.PunchPolicyType;
import com.habilisoft.doce.api.domain.model.punch.policy.TimeRangePunchPolicy;
import com.habilisoft.doce.api.domain.repositories.EmployeeRepository;
import com.habilisoft.doce.api.domain.repositories.TimeAttendanceRecordRepository;
import com.habilisoft.doce.api.domain.util.DateUtil;
import com.habilisoft.doce.api.domain.model.punch.PunchType;
import com.habilisoft.doce.api.persistence.mapping.AmbulatoryAssistanceDetailReport;
import com.habilisoft.doce.api.persistence.mapping.EmployeesWorkHourReport;
import com.habilisoft.doce.api.persistence.mapping.TimeAttendanceRecordReport;
import com.habilisoft.doce.api.persistence.mapping.TimeAttendanceSummaryReport;
import com.habilisoft.doce.api.persistence.mapping.TimeAttendanceSummaryReportDetail;
import com.habilisoft.doce.api.persistence.repositories.TimeAttendanceRecordJpaRepository;
import com.habilisoft.doce.api.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 2019-04-21.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TimeAttendanceRecordService {
    private final TimeAttendanceRecordRepository repository;
    private final TimeAttendanceRecordJpaRepository jpaRepository;
    private final EmployeeRepository employeeRepository;
    private final Integer deviationAllowMinutes = 15;
    //Time between two punches to be considered consecutives
    private final Integer CONSIDERED_CONSECUTIVE_MINUTES = 2;

    @Transactional
    public TimeAttendanceRecord update(TimeAttendanceRecord record) {
        updateRecord(record);
        return repository.save(record);
    }

    @Transactional
    public void update(List<TimeAttendanceRecord> data) {
        data.forEach(this::updateRecord);
    }

    private WorkShiftDetail getCurrentDaySchedule(WorkShift workShift, Date date) {
        return workShift
                .getDetails()
                .stream()
                .filter(w -> w.getWeekDay().getName().equalsIgnoreCase(DateUtil.getDayName(date)))
                .findFirst()
                .orElse(null);
    }

    private void updateRecord(final TimeAttendanceRecord record) {
        final Employee employee = employeeRepository
                .findById(record.getEmployee().getId())
                .orElseThrow(() -> new EmployeeNotFoundException(record.getEmployee().getId()));
        //TODO: Optimize, getting too many details from database. Should be a single query to get employee, workshift and day detail
        WorkShift workShift = employee.getWorkShift();
        if(workShift == null) {
            record.setPunchType(PunchType.NO_WORK_SHIFT);
            repository.save(record);
            return;
        }
        record.setWorkShift(workShift);
        Date currentPunchTime = record.getTime();
        WorkShiftDetail schedule = getCurrentDaySchedule(workShift, currentPunchTime);

        if (workShift.getPunchPolicy().getType() == PunchPolicyType.IN_TIME_RANGE) {
            processTimeRangePolicy(record, workShift, currentPunchTime, schedule);
        } else {
            processLastPunchIsOutPolicy(record, employee, workShift, currentPunchTime, schedule);
        }
    }

    private void processTimeRangePolicy(TimeAttendanceRecord record, WorkShift workShift, Date currentPunchTime, WorkShiftDetail schedule) {
        Long lateGracePeriod = workShift.getLateGracePeriod();
        TimeRangePunchPolicy policy = (TimeRangePunchPolicy) workShift.getPunchPolicy();
        LocalTime punchTime = DateUtils.getTime(currentPunchTime);

        if(DateUtils.isWithinRange(policy.getInStart(), policy.getInEnd(), punchTime)) {
            record.setPunchType(PunchType.IN);
        } else {
            record.setPunchType(PunchType.OUT);
        }

        if(record.getPunchType() == PunchType.OUT) {
            record.setDifferenceInSeconds(getDifferenceInSeconds(currentPunchTime, schedule.getEndTime()));
            record.setIsEarlyDeparture(isEarlyDeparture(record.getDifferenceInSeconds(), lateGracePeriod));
        } else {
            record.setDifferenceInSeconds(getDifferenceInSeconds(currentPunchTime, schedule.getStartTime()));
            record.setIsLateArrival(isLateArrival(record.getDifferenceInSeconds(), lateGracePeriod));
        }
        repository.save(record);

    }
    private void processLastPunchIsOutPolicy(TimeAttendanceRecord record, Employee employee, WorkShift workShift, Date currentPunchTime, WorkShiftDetail schedule) {
        Long lateGracePeriod = workShift.getLateGracePeriod();

        if(schedule == null) {
            log.info(
                    "Employee's work shift doesn't have schedule for the current day {}",
                    kv("employee", employee)
            );
            record.setPunchType(PunchType.NOT_IN_SCHEDULE);
            repository.save(record);
            return;
        }

        //Get previous punch
        final TimeAttendanceRecord lastPunch = getLastPunchFromCurrentSchedule(employee, schedule, currentPunchTime);

        if(lastPunch == null) {
            record.setPunchType(PunchType.IN);
            record.setDifferenceInSeconds(getDifferenceInSeconds(currentPunchTime, schedule.getStartTime()));
            record.setIsLateArrival(isLateArrival(record.getDifferenceInSeconds(), lateGracePeriod));
            repository.save(record);
        } else {
            if(PunchType.IN.equals(lastPunch.getPunchType())) {
                long differenceBetweenCurrentAndPreviousPunch = getDifferenceInMinutes(lastPunch.getTime(), currentPunchTime);
                if(differenceBetweenCurrentAndPreviousPunch > CONSIDERED_CONSECUTIVE_MINUTES) {
                    record.setPunchType(PunchType.OUT);
                    record.setDifferenceInSeconds(getDifferenceInSeconds(currentPunchTime, schedule.getEndTime()));
                    record.setIsEarlyDeparture(isEarlyDeparture(record.getDifferenceInSeconds(), lateGracePeriod));
                    repository.save(record);
                }
            } else if (PunchType.OUT.equals(lastPunch.getPunchType())) {
                lastPunch.setTime(currentPunchTime);
                lastPunch.setRecordDate(record.getTime());
                lastPunch.setDifferenceInSeconds(getDifferenceInSeconds(currentPunchTime, schedule.getEndTime()));
                lastPunch.setIsEarlyDeparture(isEarlyDeparture(lastPunch.getDifferenceInSeconds(), lateGracePeriod));
                repository.save(lastPunch);
            }
        }
    }

    public boolean isEarlyDeparture(Long differenceInSeconds, Long lateGracePeriod) {
        Long minutes = DateUtils.secondsToMinutes(differenceInSeconds);
        return (minutes + lateGracePeriod) < 0;
    }

    public boolean isLateArrival(Long differenceInSeconds, Long lateGracePeriod) {
        Long minutes = DateUtils.secondsToMinutes(differenceInSeconds);
        return minutes > lateGracePeriod;
    }

    public long getDifferenceInSeconds(Date punchDate, LocalTime scheduled) {
        LocalTime punchTime = DateUtils.getTime(punchDate);
        return scheduled.until(punchTime, ChronoUnit.SECONDS);
    }

    public long getDifferenceInSeconds(Date currentDate, Date previousDate) {
        LocalTime currentTime = DateUtils.getTime(currentDate);
        LocalTime previousTime = DateUtils.getTime(previousDate);
        return currentTime.until(previousTime, ChronoUnit.SECONDS);
    }

    public long getDifferenceInMinutes(Date currentDate, Date previousDate) {
        LocalTime currentTime = DateUtils.getTime(currentDate);
        LocalTime previousTime = DateUtils.getTime(previousDate);
        return currentTime.until(previousTime, ChronoUnit.MINUTES);
    }

    private TimeAttendanceRecord getLastPunchFromCurrentSchedule(Employee employee, WorkShiftDetail detail, Date recordDate) {
        final Pageable pageable = PageRequest.of(0, 1);
        final Page<TimeAttendanceRecord> timeAttendanceRecordPage = this.repository
                .findEmployeeEntries(employee.getId(), recordDate, pageable);

        if(timeAttendanceRecordPage.getContent().size() == 0) {
            return null;
        }
        return timeAttendanceRecordPage.getContent().get(0);
    }

   public Page<TimeAttendanceRecordReport> getEmployeeAttendanceReport(final Map<String, Object> queryMap,
                                                                       final Pageable pageable) throws ParseException {

        final Date dateStart = queryMap.containsKey("recordDateStart") ? DateUtil.parse(queryMap.get("recordDateStart").toString()) : new Date();
        final Date dateEnd = queryMap.containsKey("recordDateEnd") ? DateUtil.parse(queryMap.get("recordDateEnd").toString()) : new Date();
        final Long groupId = queryMap.containsKey("groupId") ? Long.valueOf(queryMap.get("groupId").toString()) : 0l;
        final Long employeeId = queryMap.containsKey("employeeId") ? Long.valueOf(queryMap.get("employeeId").toString()) : 0l;

        return jpaRepository.getTimeAttendanceReport(dateStart, dateEnd, groupId, employeeId, pageable);

    }


    public List<TimeAttendanceSummaryReport> getEmployeeTimeAttendanceSummary(final Map<String, Object> queryMap) throws ParseException {
        final Date startDate = queryMap.containsKey("startDate") ? DateUtil.parse(queryMap.get("startDate").toString()) : new Date();
        final Date endDate = queryMap.containsKey("endDate") ? DateUtil.parse(queryMap.get("endDate").toString()) : new Date();

        return jpaRepository.findTimeAttendanceSummary(startDate, endDate);
    }



    public Page<TimeAttendanceSummaryReportDetail> getTimeAttendanceSummaryDetail(final Map<String, Object> queryMap,
                                                                                  final Pageable pageable) throws ParseException {
        final Integer typeId = queryMap.containsKey("typeId") ? Integer.valueOf(queryMap.get("typeId").toString()) : 0;
        final Date recordDate = queryMap.containsKey("recordDate") ? DateUtil.parse(queryMap.get("recordDate").toString()) : new Date();

        return jpaRepository.findTimeAttendanceSummaryDetail(typeId, recordDate, pageable);

    }

    public List<Map> getEmployeeWorkHourByRange(final Map<String, Object> queryMap) throws ParseException {
        final String filterType = queryMap.containsKey("filterType") ? queryMap.get("filterType").toString() : "week";
        final Date filterDate = queryMap.containsKey("filterDate") ? DateUtil.parse(queryMap.get("filterDate").toString()) : new Date();
        final Date filterDateEnd = queryMap.containsKey("filterDateEnd") ? DateUtil.parse(queryMap.get("filterDateEnd").toString()) : new Date();
        final Long groupId = queryMap.containsKey("groupId") ? Long.valueOf(queryMap.get("groupId").toString()) : 0l;
        final Long locationId = queryMap.containsKey("locationId") ? Long.valueOf(queryMap.get("locationId").toString()) : 0l;
        final Long employeeId = queryMap.containsKey("employeeId") ? Long.valueOf(queryMap.get("employeeId").toString()) : 0l;

        Map<Map, List<EmployeesWorkHourReport>> employeesMap = jpaRepository
                .findEmployeeWorkHoursByRange(filterType, filterDate, filterDateEnd, groupId, locationId, employeeId)
                .stream()
                .collect(groupingBy(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", r.getId());
                    map.put("name", r.getName());

                    return map;
                }));

        return employeesMap.entrySet()
                .stream()
                .map(e -> {
                    Map<String, Object> mappedMap = e.getKey();
                    mappedMap.put("workHours", e.getValue());
                    return mappedMap;
                })
                .collect(Collectors.toList());
    }



    public Page<AmbulatoryAssistanceDetailReport> getEmployeeWorkHourDetails(final Long employeeId,
                                                                             final Date recordDate,
                                                                             final Pageable pageable) {
        return jpaRepository.findEmployeeWorkHoursDetails(employeeId, recordDate, pageable);
    }

    /*public List<Map> getAmbulatoryEmployeeAssistanceByRange(final Map<String, Object> queryMap) throws ParseException {

        final String filterType = queryMap.containsKey("filterType") ? queryMap.get("filterType").toString() : "week";
        final Date filterDate = queryMap.containsKey("filterDate") ? DateUtil.parse(queryMap.get("filterDate").toString()) : new Date();
        final Date filterDateEnd = queryMap.containsKey("filterDateEnd") ? DateUtil.parse(queryMap.get("filterDateEnd").toString()) : new Date();
        final Long groupId = queryMap.containsKey("groupId") ? Long.valueOf(queryMap.get("groupId").toString()) : 0l;
        final Long employeeId = queryMap.containsKey("employeeId") ? Long.valueOf(queryMap.get("employeeId").toString()) : 0l;

        Map<Map, List<AmbulatoryAssistanceSummaryReport>> employeesMap = this.repository
                .findAmbulatoryEmployeeAssistanceByRange(filterType, filterDate, filterDateEnd, groupId, employeeId)
                .stream()
                .collect(groupingBy(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", r.getId());
                    map.put("name", r.getName());
                    return map;
                }));

        return employeesMap.entrySet()
                .stream()
                .map(e -> {
                    Map<String, Object> mappedMap = e.getKey();
                    mappedMap.put("visits", e.getValue());
                    return mappedMap;
                })
                .collect(Collectors.toList());
    }

    public Page<AmbulatoryAssistanceDetailReport> getAmbulatoryEmployeeAssistanceDetails(final Long employeeId,
                                                                                         final Date recordDate,
                                                                                         final Pageable pageable) {

        return this.repository.findAmbulatoryEmployeeAssistanceDetails(employeeId, recordDate, pageable);
    }*/

}
