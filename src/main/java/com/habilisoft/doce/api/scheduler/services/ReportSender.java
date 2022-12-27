package com.habilisoft.doce.api.scheduler.services;

import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.habilisoft.doce.api.domain.reports.early_departures.EarlyDeparturesReportService;
import com.habilisoft.doce.api.domain.reports.late_arrivals.LateArrivalsReportService;
import com.habilisoft.doce.api.email.models.Attachment;
import com.habilisoft.doce.api.email.models.PlainTextEmailRequest;
import com.habilisoft.doce.api.email.services.MailService;
import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.model.SendReportTask;
import com.habilisoft.doce.api.utils.DateUtils;
import com.habilisoft.doce.api.utils.DefaultTimeZone;
import com.habilisoft.doce.api.utils.export.domain.UserFilter;
import com.habilisoft.doce.api.utils.export.dto.ExportRequest;
import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 3/12/22.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReportSender {
    private final LateArrivalsReportService lateArrivalsReportService;
    private final EarlyDeparturesReportService earlyDeparturesReportService;
    private final MailService mailService;

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

    private Resource getReportResource(ScheduledReport report) throws CsvRequiredFieldEmptyException, DocumentException, CsvDataTypeMismatchException, IOException {
        return switch (report.getReport()) {
            case LATE_ARRIVALS -> getLateArrivals(report);
            case EARLY_DEPARTURES -> getEarlyDepartures(report);
        };
    }

    private Resource getLateArrivals(ScheduledReport report) throws CsvRequiredFieldEmptyException, DocumentException, CsvDataTypeMismatchException, IOException {
        ExportRequest exportRequest = getExportRequest(report);
        return lateArrivalsReportService.getReport(exportRequest);
    }

    private Resource getEarlyDepartures(ScheduledReport report) throws CsvRequiredFieldEmptyException, DocumentException, CsvDataTypeMismatchException, IOException {
        ExportRequest exportRequest = getExportRequest(report);
        return earlyDeparturesReportService.getReport(exportRequest);
    }

    private ExportRequest getExportRequest(ScheduledReport report) {
        ExportRequest exportRequest = ExportRequest.builder()
                .exportType(report.getReportFormat())
                .build();
        List<UserFilter> userFilters = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        String dateString = DateUtils.getCurrentDateStringTimezone(DefaultTimeZone.getDefault().getID());
        String dateCaption = DateUtils.monthNameDayAndYear(DefaultTimeZone.getDefault().getID(), Locale.forLanguageTag("es-ES"));
        params.put("date", dateString);
        userFilters.add(
                UserFilter.builder()
                        .key("Fecha")
                        .value(dateCaption)
                        .build());

        if (report.getGroup() != null) {
            params.put("group", String.valueOf(report.getGroup().getId()));
            userFilters.add(
                    UserFilter.builder()
                            .key("Grupo")
                            .value(report.getGroup().getName())
                            .build());
        }

        exportRequest.setUserFilters(userFilters);
        exportRequest.setParams(params);
        return  exportRequest;
    }


}
