package com.habilisoft.doce.api.scheduler.services;

import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.habilisoft.doce.api.email.models.PlainTextEmailRequest;
import com.habilisoft.doce.api.email.services.MailService;
import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.model.SendReportTask;
import com.habilisoft.doce.api.persistence.scheduler.repositories.ScheduledReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 3/12/22.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReportSender {
    private final ScheduledReportRepository scheduledReportRepository;
    private final MailService mailService;

    public void sendReport(SendReportTask task) {
        String tenant = task.getTenant();
        log.info("Sending Scheduled Report {} {}",
                kv("tenant", tenant),
                kv("task", task));

        ScheduledReport report = task.getScheduledReport();

        mailService.sendPlainTextMessage(
                PlainTextEmailRequest.builder()
                        .subject("Reporte")
                        .to(report.getRecipients())
                        .text("El reporte aqui")
                        .build()
        );

        TenantContext.setCurrentTenant(task.getTenant());

    }
}
