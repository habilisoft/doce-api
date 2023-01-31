package com.habilisoft.doce.api.web.reports.domain.services;

import com.habilisoft.doce.api.email.models.Attachment;
import com.habilisoft.doce.api.email.models.PlainTextEmailRequest;
import com.habilisoft.doce.api.email.services.MailService;
import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.domain.repositories.ReportRepository;
import com.habilisoft.doce.api.reporting.export.MailReportRequest;
import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created on 21/1/23.
 */
@Service
@RequiredArgsConstructor
public class ReportMailService {
    private final ReportExportService reportExportService;
    private final MailService mailService;
    private final ReportRepository reportRepository;

    public boolean sendReport(String reportSlug, MailReportRequest request) throws CsvRequiredFieldEmptyException, DocumentException, CsvDataTypeMismatchException, IOException {
        Report report = reportRepository.findBySlug(reportSlug)
                .orElseThrow();

        Resource resource = reportExportService.export(report, request);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(resource.getInputStream().readAllBytes());

        mailService.sendPlainTextMessage(
                PlainTextEmailRequest.builder()
                        .subject(report.getName())
                        .to(request.getRecipients())
                        .attachments(
                                List.of(
                                        Attachment.builder()
                                                .name(String.format("%s.%s", report.getName(), request.getExportType().name()))
                                                .inputStream(inputStream)
                                                .build()
                                )
                        )
                        .text(report.getName())
                        .build()
        );

        return true;
    }

}
