package com.habilisoft.doce.api.persistence.entities.reports;

import com.habilisoft.doce.api.domain.model.PunchType;
import com.habilisoft.doce.api.dto.device.EventType;
import com.habilisoft.doce.api.dto.device.InOut;
import com.habilisoft.doce.api.dto.device.LogRecordMode;
import com.habilisoft.doce.api.persistence.entities.DeviceEntity;
import com.habilisoft.doce.api.persistence.entities.EmployeeEntity;
import com.habilisoft.doce.api.persistence.entities.WorkShiftEntity;
import lombok.Data;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created on 18/12/22.
 */
@Entity
@Data
@Subselect("select * from time_attendance_records where is_early is true")
public class EarlyDeparturesReportEntity {

    @Id
    private Long id;

    @Column(name = "record_date")
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
    private Boolean isEarly;

    @Column(name = "is_late")
    private Boolean isLate;

    @Column(name = "difference_in_seconds")
    private Long differenceInSeconds;
}
