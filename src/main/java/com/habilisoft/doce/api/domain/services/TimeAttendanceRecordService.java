package com.habilisoft.doce.api.domain.services;

import com.habilisoft.doce.api.domain.exceptions.EmployeeNotFoundException;
import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.model.TimeAttendanceRecord;
import com.habilisoft.doce.api.domain.model.WorkShiftDetail;
import com.habilisoft.doce.api.domain.repositories.EmployeeRepository;
import com.habilisoft.doce.api.domain.repositories.TimeAttendanceRecordRepository;
import com.habilisoft.doce.api.domain.util.DateUtil;
import com.habilisoft.doce.api.domain.model.PunchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created on 2019-04-21.
 */
@Service
public class TimeAttendanceRecordService {
    private final TimeAttendanceRecordRepository repository;
    private final EmployeeRepository employeeRepository;
    private final Integer deviationAllowMinutes = 15;

    public TimeAttendanceRecordService(final TimeAttendanceRecordRepository repository,
                                       final EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public TimeAttendanceRecord update(TimeAttendanceRecord record) {
        record.setPunchType(this.calcTimeRecordPunchTime(record));
        record.setWorkShift(this.employeeRepository.getEmployeeWorkShift(record.getEmployee().getId()));
        return null;
        //return super.update(record);
    }

    @Transactional
    public List<TimeAttendanceRecord> update(List<TimeAttendanceRecord> data) {

        data.forEach(e -> {
            e.setPunchType(this.calcTimeRecordPunchTime(e));
            e.setWorkShift(this.employeeRepository.getEmployeeWorkShift(e.getEmployee().getId()));
        });

        return repository.saveAll(data);
    }

    private PunchType calcTimeRecordPunchTime(final TimeAttendanceRecord record) {

        final Pageable pageable = PageRequest.of(0, 1);
        final Page<TimeAttendanceRecord> timeAttendanceRecordPage = this.repository
                .findEmployeeEntries(record.getEmployee().getId(), new Date(), record.getDevice().getSerialNumber(), pageable);

        if (timeAttendanceRecordPage.getContent().size() == 0)
            return PunchType.IN;

        final TimeAttendanceRecord lastTimeAttendanceRecord = timeAttendanceRecordPage.getContent().get(0);
        final Employee employee = employeeRepository
                .findById(record.getEmployee().getId())
                .orElseThrow(() -> new EmployeeNotFoundException(record.getEmployee().getId()));

        if (employee.getWorkShift() == null)
            return (lastTimeAttendanceRecord.getPunchType() == PunchType.IN) ? PunchType.OUT : PunchType.IN;

        Optional<WorkShiftDetail> workShiftDetailOptional = employee
                .getWorkShift()
                .getDetails()
                .stream()
                .filter(w -> w.getWeekDay().getName().equalsIgnoreCase(DateUtil.getDayName(new Date())))
                .findFirst();

        if (workShiftDetailOptional.isEmpty()) {
            return (lastTimeAttendanceRecord.getPunchType() == PunchType.IN) ? PunchType.OUT : PunchType.IN;
        }

        final WorkShiftDetail workShiftDetail = workShiftDetailOptional.get();

        final Date startLimitBreakStart = new Date();//DateUtil.addMinutesWithNow(workShiftDetail.getBreakStartTime()., deviationAllowMinutes * -1);
        final Date endLimitBreakStart =  new Date();//DateUtil.addMinutesWithNow(workShiftDetail.getBreakStartTime(), deviationAllowMinutes);

        final Date startLimitBreakEnd =  new Date();//DateUtil.addMinutesWithNow(workShiftDetail.getBreakEndTime(), deviationAllowMinutes * -1);
        final Date endLimitBreakEnd =  new Date();//DateUtil.addMinutesWithNow(workShiftDetail.getBreakEndTime(), deviationAllowMinutes);

        if (DateUtil.between(record.getTime(), startLimitBreakStart, endLimitBreakStart)
                && lastTimeAttendanceRecord.getPunchType() == PunchType.IN)
            return PunchType.START_BREAK;

        if (DateUtil.between(record.getTime(), startLimitBreakEnd, endLimitBreakEnd)
                && lastTimeAttendanceRecord.getPunchType() == PunchType.START_BREAK)
            return PunchType.END_BREAK;

        if (lastTimeAttendanceRecord.getPunchType() == PunchType.IN || lastTimeAttendanceRecord.getPunchType() == PunchType.END_BREAK)
            return PunchType.OUT;
        else if (lastTimeAttendanceRecord.getPunchType() == PunchType.OUT || lastTimeAttendanceRecord.getPunchType() == PunchType.START_BREAK)
            return PunchType.IN;

        return (lastTimeAttendanceRecord.getPunchType() == PunchType.IN) ? PunchType.OUT : PunchType.IN;
    }

   /* public Page<TimeAttendanceRecordReport> getEmployeeAttendanceReport(final Map<String, Object> queryMap,
                                                                        final Pageable pageable) throws ParseException {

        final Date dateStart = queryMap.containsKey("recordDateStart") ? DateUtil.parse(queryMap.get("recordDateStart").toString()) : new Date();
        final Date dateEnd = queryMap.containsKey("recordDateEnd") ? DateUtil.parse(queryMap.get("recordDateEnd").toString()) : new Date();
        final Long departmentId = queryMap.containsKey("departmentId") ? Long.valueOf(queryMap.get("departmentId").toString()) : 0l;
        final Long employeeId = queryMap.containsKey("employeeId") ? Long.valueOf(queryMap.get("employeeId").toString()) : 0l;

        return this.repository.getTimeAttendanceReport(dateStart, dateEnd, departmentId, employeeId, pageable);

    }

    public List<TimeAttendanceSummaryReport> getEmployeeTimeAttendanceSummary(final Map<String, Object> queryMap) throws ParseException {
        final Date startDate = queryMap.containsKey("startDate") ? DateUtil.parse(queryMap.get("startDate").toString()) : new Date();
        final Date endDate = queryMap.containsKey("endDate") ? DateUtil.parse(queryMap.get("endDate").toString()) : new Date();

        return this.repository.findTimeAttendanceSummary(startDate, endDate);
    }

    public Page<TimeAttendanceSummaryReportDetail> getTimeAttendanceSummaryDetail(final Map<String, Object> queryMap,
                                                                                  final Pageable pageable) throws ParseException {
        final Integer typeId = queryMap.containsKey("typeId") ? Integer.valueOf(queryMap.get("typeId").toString()) : 0;
        final Date recordDate = queryMap.containsKey("recordDate") ? DateUtil.parse(queryMap.get("recordDate").toString()) : new Date();

        return this.repository.findTimeAttendanceSummaryDetail(typeId, recordDate, pageable);

    }*/

   /* public List<Map> getEmployeeWorkHourByRange(final Map<String, Object> queryMap) throws ParseException {
        final String filterType = queryMap.containsKey("filterType") ? queryMap.get("filterType").toString() : "week";
        final Date filterDate = queryMap.containsKey("filterDate") ? DateUtil.parse(queryMap.get("filterDate").toString()) : new Date();
        final Date filterDateEnd = queryMap.containsKey("filterDateEnd") ? DateUtil.parse(queryMap.get("filterDateEnd").toString()) : new Date();
        final Long departmentId = queryMap.containsKey("departmentId") ? Long.valueOf(queryMap.get("departmentId").toString()) : 0l;
        final Long locationId = queryMap.containsKey("locationId") ? Long.valueOf(queryMap.get("locationId").toString()) : 0l;
        final Long employeeId = queryMap.containsKey("employeeId") ? Long.valueOf(queryMap.get("employeeId").toString()) : 0l;

        Map<Map, List<EmployeesWorkHourReport>> employeesMap = this.repository
                .findEmployeeWorkHoursByRange(filterType, filterDate, filterDateEnd, departmentId, locationId, employeeId)
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

        return this.repository.findEmployeeWorkHoursDetails(employeeId, recordDate, pageable);
    }*/

    /*public List<Map> getAmbulatoryEmployeeAssistanceByRange(final Map<String, Object> queryMap) throws ParseException {

        final String filterType = queryMap.containsKey("filterType") ? queryMap.get("filterType").toString() : "week";
        final Date filterDate = queryMap.containsKey("filterDate") ? DateUtil.parse(queryMap.get("filterDate").toString()) : new Date();
        final Date filterDateEnd = queryMap.containsKey("filterDateEnd") ? DateUtil.parse(queryMap.get("filterDateEnd").toString()) : new Date();
        final Long departmentId = queryMap.containsKey("departmentId") ? Long.valueOf(queryMap.get("departmentId").toString()) : 0l;
        final Long employeeId = queryMap.containsKey("employeeId") ? Long.valueOf(queryMap.get("employeeId").toString()) : 0l;

        Map<Map, List<AmbulatoryAssistanceSummaryReport>> employeesMap = this.repository
                .findAmbulatoryEmployeeAssistanceByRange(filterType, filterDate, filterDateEnd, departmentId, employeeId)
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
