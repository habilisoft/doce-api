package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.domain.model.TimeAttendanceRecord;
import com.habilisoft.doce.api.domain.repositories.TimeAttendanceRecordRepository;
import com.habilisoft.doce.api.persistence.converters.TimeAttendanceRecordJpaConverter;
import com.habilisoft.doce.api.persistence.entities.TimeAttendanceRecordEntity;
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
    public Page<TimeAttendanceRecord> findEmployeeEntries(Long id, Date date, Pageable pageable) {
        Page<TimeAttendanceRecordEntity> entityPage = jpaRepo.findEmployeeEntries(id, date, pageable);
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

    @Override
    public TimeAttendanceRecord save(TimeAttendanceRecord record) {
        TimeAttendanceRecordEntity entity = converter.toJpaEntity(record);
        jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }
}
