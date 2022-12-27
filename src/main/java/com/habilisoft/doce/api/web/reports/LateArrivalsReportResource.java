package com.habilisoft.doce.api.web.reports;

import com.habilisoft.doce.api.domain.reports.late_arrivals.LateArrivalsReportService;
import com.habilisoft.doce.api.domain.reports.late_arrivals.LateDeparturesExportable;
import com.habilisoft.doce.api.persistence.repositories.reports.EarlyDeparturesAndArrivalsJpaReportRepository;
import com.habilisoft.doce.api.persistence.repositories.reports.LateArrivalResponse;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created on 18/12/22.
 */
@RestController
@RequestMapping("/reports/late-arrivals")
@RequiredArgsConstructor
public class LateArrivalsReportResource {
    private final EarlyDeparturesAndArrivalsJpaReportRepository earlyDeparturesReportRepository;
    private final LateArrivalsReportService lateArrivalsReportService;

    @GetMapping
    public Page<?> getLateArrivalsReport(@RequestParam(name = "date", defaultValue = "") String date,
                                         @RequestParam(name = "group", defaultValue = "") String groupId,
                                         @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                                         @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                                         @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) throws ParseException {

        final Pageable pageable = PageRequest.of(page, size);
        return earlyDeparturesReportRepository.getLateArrivals(date, groupId, pageable);
    }

    @PostMapping(value = "/export")
    public ResponseEntity<Resource> export(
            @RequestParam(name = "date", defaultValue = "") String date,
            @RequestParam(name = "group", defaultValue = "") String group,
            @RequestBody ExportRequest exportRequest) throws ParseException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, DocumentException, IOException {

        exportRequest.setParams(Map.of(
                "date", date,
                "group", group
        ));

        Resource resource = lateArrivalsReportService.getReport(exportRequest);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                .body(resource);
    }

}
