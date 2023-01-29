package com.habilisoft.doce.api.scheduler.services;

import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.habilisoft.doce.api.email.models.Attachment;
import com.habilisoft.doce.api.email.models.PlainTextEmailRequest;
import com.habilisoft.doce.api.email.services.MailService;
import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.domain.model.ReportUIFilter;
import com.habilisoft.doce.api.reporting.domain.repositories.ReportRepository;
import com.habilisoft.doce.api.reporting.export.ExportRequest;
import com.habilisoft.doce.api.reporting.export.UserFilter;
import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.model.SendReportTask;
import com.habilisoft.doce.api.utils.DateUtils;
import com.habilisoft.doce.api.utils.DefaultTimeZone;
import com.habilisoft.doce.api.web.reports.domain.services.ReportExportService;
import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 3/12/22.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReportSender {
    private final MailService mailService;
    private final ReportExportService reportExportService;
    private final ReportRepository reportRepository;

    public void sendReport(SendReportTask task) {
        try {
            String tenant = task.getTenant();
            TenantContext.setCurrentTenant(task.getTenant());

            log.info("Sending Scheduled Report {} {}",
                    kv("tenant", tenant),
                    kv("task", task));

            ScheduledReport report = task.getScheduledReport();
            Resource resource = getReportResource(report);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(resource.getInputStream().readAllBytes());

            mailService.sendPlainTextMessage(
                    PlainTextEmailRequest.builder()
                            .subject(report.getDescription())
                            .to(report.getRecipients())
                            .attachments(
                                    List.of(
                                            Attachment.builder()
                                                    .name(String.format("%s.%s",report.getDescription(), report.getReportFormat().name()))
                                                    .inputStream(inputStream)
                                                    .build()
                                    )
                            )
                            .text(report.getDescription())
                            .build()
            );

        } catch (Exception e) {
            log.error("Could not generate report resource", e);
            throw new RuntimeException("Could not generate report resource");
        }

    }

    private Resource getReportResource(ScheduledReport scheduledReport) throws CsvRequiredFieldEmptyException, DocumentException, CsvDataTypeMismatchException, IOException {
        Report report = reportRepository.findById(scheduledReport.getReport().getId())
                .orElseThrow();

        List<UserFilter> userFilters = scheduledReport.getUserFilters();
        List<UserFilter> notRequired = report.getUiFilters()
                .stream()
                .filter( filter -> BooleanUtils.isNotTrue(filter.getRequired()))
                .map(this::getFilterDefaultValue)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        userFilters.addAll(notRequired);

        ExportRequest exportRequest = ExportRequest.builder()
                .exportType(scheduledReport.getReportFormat())
                .userFilters(scheduledReport.getUserFilters())
                .build();

        return reportExportService.export(report, exportRequest);
    }

    private UserFilter getFilterDefaultValue(ReportUIFilter filter) {
        return switch (filter.getType()) {
            case DATE -> UserFilter.builder()
                    .field(filter.getField())
                    .displayName(filter.getDisplayName())
                    .value(DateUtils.getCurrentDateStringTimezone(DefaultTimeZone.getDefault().getID()))
                    .displayValue(DateUtils.monthNameDayAndYear(DefaultTimeZone.getDefault().getID(), Locale.forLanguageTag("es-ES")))
                    .build();
            //TODO: More to add in the future
            default -> null;
        };
    }

}
