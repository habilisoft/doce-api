package com.habilisoft.doce.api.persistence.scheduler.entitites;

import com.habilisoft.doce.api.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created on 7/12/22.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "send_report_tasks")
public class SendReportTaskEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "send_report_tasks_id_seq", sequenceName = "send_report_tasks_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "send_report_tasks_id_seq")
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private ScheduledReportEntity scheduledReport;
    private String timeZone;
    private String cronExpression;
}
