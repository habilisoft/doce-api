package com.armando.timeattendance.api.persistence.entities;

import com.armando.timeattendance.api.domain.model.WorkShiftDetail;
import com.armando.timeattendance.api.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 2019-04-27.
 */
@Data
@Entity
@Table(name = "work_shifts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "deleted='false'")
@SQLDelete(sql = "update work_shifts set deleted = true where id = ?")
public class WorkShiftEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "work_shifts_id_seq", sequenceName = "work_shifts_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_shifts_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "week_work_hours")
    private Float weekWorkHours;

    @Column
    private Integer lateGracePeriod;

    @Column
    private Boolean punchForBreak;

    @Column
    private Integer breakMinutes;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<WorkShiftDetail> details;

    public Set<WorkShiftDetail> getDetails() {
        if (this.details == null)
            return null;

        return this.details
                .stream()
                .sorted(Comparator.comparingInt(a -> a.getWeekDay().ordinal()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}