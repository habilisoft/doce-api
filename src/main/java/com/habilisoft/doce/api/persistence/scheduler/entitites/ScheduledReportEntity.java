package com.habilisoft.doce.api.persistence.scheduler.entitites;

import com.habilisoft.doce.api.persistence.BaseEntity;
import com.habilisoft.doce.api.reporting.export.UserFilter;
import com.habilisoft.doce.api.reporting.export.enums.ExportType;
import com.habilisoft.doce.api.reporting.persistence.entities.ReportEntity;
import com.habilisoft.doce.api.scheduler.model.ReportScheduleEntry;
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

    @OneToOne
    private ReportEntity report;

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

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<UserFilter> userFilters;
}
