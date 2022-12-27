package com.habilisoft.doce.api.persistence.scheduler.entitites;

import com.habilisoft.doce.api.domain.model.Report;
import com.habilisoft.doce.api.persistence.BaseEntity;
import com.habilisoft.doce.api.persistence.entities.GroupEntity;
import com.habilisoft.doce.api.scheduler.model.ReportScheduleEntry;
import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.utils.export.enums.ExportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * Created on 3/12/22.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scheduled_reports")
public class ScheduledReportEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "scheduled_reports_id_seq", sequenceName = "scheduled_reports_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheduled_reports_id_seq")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Report report;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> recipients;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<ReportScheduleEntry> scheduleEntries;

    @Column
    @Enumerated(EnumType.STRING)
    private ExportType reportFormat;

    @Column
    private Boolean previousDateData;

    @OneToOne
    private GroupEntity group;

   /* @OneToMany
    @JoinTable(
            name="scheduled_report_groups",
            joinColumns = @JoinColumn( name="scheduled_report_id"),
            inverseJoinColumns = @JoinColumn( name="group_id")
    )
    List<GroupEntity> groups;*/

}
