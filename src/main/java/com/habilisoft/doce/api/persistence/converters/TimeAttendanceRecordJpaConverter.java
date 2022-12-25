package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.TimeAttendanceRecord;
import com.habilisoft.doce.api.persistence.entities.TimeAttendanceRecordEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 8/22/22.
 */
@Component
@RequiredArgsConstructor
public class TimeAttendanceRecordJpaConverter implements JpaConverter<TimeAttendanceRecord, TimeAttendanceRecordEntity> {
    private final ModelMapper  modelMapper;
    private final DeviceJpaConverter deviceJpaConverter;
    private final EmployeeJpaConverter employeeJpaConverter;
    private final WorkShiftJpaConverter workShiftJpaConverter;

    @Override
    public TimeAttendanceRecord fromJpaEntity(TimeAttendanceRecordEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(d -> TimeAttendanceRecord.builder()
                        .id(d.getId())
                        .time(d.getTime())
                        .event(d.getEvent())
                        .inOut(d.getInOut())
                        .device(deviceJpaConverter.fromJpaEntity(d.getDevice()))
                        .employee(employeeJpaConverter.fromJpaEntity(d.getEmployee()))
                        .recordDate(d.getRecordDate())
                        .isLateArrival(d.getIsLateArrival())
                        .isEarlyDeparture(d.getIsEarlyDeparture())
                        .differenceInSeconds(d.getDifferenceInSeconds())
                        .punchType(d.getPunchType())
                        .mode(d.getMode())
                        .workShift(workShiftJpaConverter.fromJpaEntity(d.getWorkShift()))
                        .build()
                ).orElse(null);
    }

    @Override
    public TimeAttendanceRecordEntity toJpaEntity(TimeAttendanceRecord domainObject) {
        return Optional.ofNullable(domainObject)
                .map(d -> TimeAttendanceRecordEntity.builder()
                        .time(d.getTime())
                        .event(d.getEvent())
                        .inOut(d.getInOut())
                        .id(d.getId())
                        .device(deviceJpaConverter.toJpaEntity(d.getDevice()))
                        .employee(employeeJpaConverter.toJpaEntity(d.getEmployee()))
                        .recordDate(d.getRecordDate())
                        .isLateArrival(d.getIsLateArrival())
                        .isEarlyDeparture(d.getIsEarlyDeparture())
                        .differenceInSeconds(d.getDifferenceInSeconds())
                        .punchType(d.getPunchType())
                        .mode(d.getMode())
                        .workShift(workShiftJpaConverter.toJpaEntity(d.getWorkShift()))
                        .build()
                ).orElse(null);
    }
}
