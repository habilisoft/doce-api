package com.armando.timeattendance.api.persistence.repositories;

import com.armando.timeattendance.api.domain.model.TimeAttendanceRecord;
import com.armando.timeattendance.api.domain.repositories.TimeAttendanceRecordRepository;
import com.armando.timeattendance.api.persistence.converters.TimeAttendanceRecordJpaConverter;
import com.armando.timeattendance.api.persistence.entities.TimeAttendanceRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 8/21/22.
 */
@Repository
public class DefaultTimeAttendanceRecordRepository implements TimeAttendanceRecordRepository {
    private final TimeAttendanceRecordJpaRepository jpaRepo;
    private final TimeAttendanceRecordJpaConverter converter;

    public DefaultTimeAttendanceRecordRepository(final TimeAttendanceRecordJpaRepository jpaRepo,
                                                 final TimeAttendanceRecordJpaConverter converter) {
        this.jpaRepo = jpaRepo;
        this.converter = converter;
    }

    @Override
    public Page<TimeAttendanceRecord> findEmployeeEntries(Long id, Date date, String serialNumber, Pageable pageable) {
        Page<TimeAttendanceRecordEntity> entityPage = jpaRepo.findEmployeeEntries(id, date, serialNumber, pageable);
        return entityPage.map(converter::fromJpaEntity);
    }

    @Override
    public List<TimeAttendanceRecord> saveAll(List<TimeAttendanceRecord> data) {
        List<TimeAttendanceRecordEntity> entityList =
                data.stream()
                .map(converter::toJpaEntity)
                .collect(Collectors.toList());

        jpaRepo.saveAll(entityList);

        return entityList.stream()
                .map(converter::fromJpaEntity)
                .collect(Collectors.toList());
    }
}
