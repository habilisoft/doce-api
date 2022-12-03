package com.armando.timeattendance.api.scheduler.entitites;

import com.armando.timeattendance.api.domain.model.Report;
import com.armando.timeattendance.api.scheduler.model.ReportScheduleEntry;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * Created on 3/12/22.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scheduled_reports")
public class ScheduledReportEntity {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public List<ReportScheduleEntry> getScheduleEntries() {
        return scheduleEntries;
    }

    public void setScheduleEntries(List<ReportScheduleEntry> scheduleEntries) {
        this.scheduleEntries = scheduleEntries;
    }
}
