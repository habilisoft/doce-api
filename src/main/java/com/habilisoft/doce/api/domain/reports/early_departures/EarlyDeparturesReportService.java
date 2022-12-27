package com.habilisoft.doce.api.domain.reports.early_departures;

import com.habilisoft.doce.api.persistence.repositories.reports.EarlyDepartureResponse;
import com.habilisoft.doce.api.persistence.repositories.reports.EarlyDeparturesAndArrivalsJpaReportRepository;
import com.habilisoft.doce.api.utils.export.domain.Exportable;
import com.habilisoft.doce.api.utils.export.domain.Exporter;
import com.habilisoft.doce.api.utils.export.domain.ExporterFactory;
import com.habilisoft.doce.api.utils.export.dto.ExportRequest;
import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 26/12/22.
 */
@Service
@RequiredArgsConstructor
public class EarlyDeparturesReportService {
    private final EarlyDeparturesAndArrivalsJpaReportRepository jpaRepo;
    public Resource getReport(ExportRequest exportRequest) throws CsvRequiredFieldEmptyException, DocumentException, CsvDataTypeMismatchException, IOException {
        Map<String, String> params = exportRequest.getParams();
        String date = params.getOrDefault("date","");
        String groupId = params.getOrDefault("group","");
        final Pageable pageable = PageRequest.of(0, 1000);
        Page<EarlyDepartureResponse> responsePage = jpaRepo.getEarlyDepartures(date, groupId, pageable);

        List<Map<String, String>> records = responsePage.getContent()
                .stream()
                .map( r -> Map.of(
                        "enrollId",String.valueOf(r.getEnroll_id()),
                        "employeeName", r.getEmployee_name(),
                        "group", r.getGroup_name(),
                        "date", r.getDateString(),
                        "hour", r.getHour(),
                        "difference", r.getDifference()
                ))
                .collect(Collectors.toList());
        exportRequest.setCaption("Reporte de Salidas Tempranas");
        Exportable exportable = new EarlyDeparturesExportable( records, exportRequest, "");
        Exporter exporter = ExporterFactory.getInstance(exportRequest.getExportType());
        return exporter.export(exportable);
    }
}
