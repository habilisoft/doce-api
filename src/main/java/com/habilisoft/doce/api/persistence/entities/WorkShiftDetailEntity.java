package com.habilisoft.doce.api.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.habilisoft.doce.api.domain.model.WorkShiftDay;
import com.habilisoft.doce.api.persistence.BaseEntity;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "work_shift_details", indexes = {
        @Index(
                name = "work_shift_details_work_shift_id", columnList = "work_shift_id"
        )
})
public class WorkShiftDetailEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private WorkShiftDay weekDay;

    @NotNull
    @Column(name = "start_time")
    @Basic
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    @Basic
    private LocalTime endTime;

    @NotNull
    @Column(name = "break_start_time")
    @Basic
    private LocalTime breakStartTime;

    @NotNull
    @Column(name = "break_end_time")
    @Basic
    private LocalTime breakEndTime;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_shift_id")
    private WorkShiftEntity workShift;

    @Column
    private Boolean selected;

    @Override
    public Long getId() {
        return this.id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkShiftDetailEntity that = (WorkShiftDetailEntity) o;
        return weekDay == that.weekDay;
    }

    @Override
    public int hashCode() {

        return Objects.hash(weekDay);
    }

}
