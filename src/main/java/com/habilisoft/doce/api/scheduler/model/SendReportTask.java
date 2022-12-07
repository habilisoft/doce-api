package com.habilisoft.doce.api.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Created on 2020-11-19.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendReportTask {
    private Long id;
    private ScheduledReport scheduledReport;
    private String timeZone;
    private String cronExpression;
    private String tenant;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendReportTask that = (SendReportTask) o;
        return id.equals(that.id) && tenant.equals(that.tenant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenant);
    }
}
