package com.habilisoft.doce.api.domain.repositories;

import com.habilisoft.doce.api.domain.model.TimeAttendanceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created on 8/21/22.
 */
public interface TimeAttendanceRecordRepository {
    Page<TimeAttendanceRecord> findEmployeeEntries(Long id, Date date, String serialNumber, Pageable pageable);

    List<TimeAttendanceRecord> saveAll(List<TimeAttendanceRecord> data);
}
