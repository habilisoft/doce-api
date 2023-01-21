package com.habilisoft.doce.api.web.reports.domain.services;

import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.domain.model.ReportSearchRequest;
import com.habilisoft.doce.api.reporting.domain.repositories.ReportRepository;
import com.habilisoft.doce.api.reporting.export.ExportRequest;
import com.habilisoft.doce.api.reporting.export.Exportable;
import com.habilisoft.doce.api.reporting.export.ExportableImpl;
import com.habilisoft.doce.api.reporting.export.Exporter;
import com.habilisoft.doce.api.reporting.export.ExporterFactory;
import com.habilisoft.doce.api.reporting.export.UserFilter;
import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 21/1/23.
 */
@Service
@RequiredArgsConstructor
public class ReportExportService {
    private final ReportSearchService reportSearchService;
    private final ReportRepository repository;

    public Resource export(String reportSlug, ExportRequest exportRequest) throws CsvRequiredFieldEmptyException, DocumentException, CsvDataTypeMismatchException, IOException {
        Report report = repository.findBySlug(reportSlug)
                .orElseThrow();

        ReportSearchRequest request = ReportSearchRequest.builder()
                .queryMap(buildQueryMapFromUserFilters(exportRequest.getUserFilters()))
                .build();

        List<Map<String, Object>> records = reportSearchService.all(report, request);
        Exporter exporter = ExporterFactory.getInstance(exportRequest.getExportType());

        Exportable exportable = ExportableImpl.builder()
                .records(records)
                .report(report)
                .userFilters(exportRequest.getUserFilters())
                .build();

        return exporter.export(exportable);
    }

    private Map<String, Object> buildQueryMapFromUserFilters(List<UserFilter> userFilters) {
        return userFilters.stream()
                .collect(Collectors.toMap(
                        UserFilter::getField,
                        UserFilter::getValue));
    }
}
