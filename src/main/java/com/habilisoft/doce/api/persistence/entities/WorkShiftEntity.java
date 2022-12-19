package com.habilisoft.doce.api.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.habilisoft.doce.api.domain.model.WorkShiftDetail;
import com.habilisoft.doce.api.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private Long lateGracePeriod;

    @Column
    private Boolean punchForBreak;

    @Column
    private Integer breakMinutes;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "workShift", fetch = FetchType.EAGER)
    private Set<WorkShiftDetailEntity> details;

    public Set<WorkShiftDetailEntity> getDetails() {
        if (this.details == null)
            return null;

        return this.details
                .stream()
                .sorted(Comparator.comparingInt(a -> a.getWeekDay().ordinal()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setDetails(Set<WorkShiftDetailEntity> details) {
        if (details != null)
            details.forEach(d -> {
                d.setWorkShift(this);
                if(BooleanUtils.isNotTrue(punchForBreak)) {
                    d.setBreakEndTime(null);
                    d.setBreakStartTime(null);
                }
            });
        this.details = details;
    }

}
