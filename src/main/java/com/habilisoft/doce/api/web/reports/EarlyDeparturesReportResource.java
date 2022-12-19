package com.habilisoft.doce.api.web.reports;

import com.habilisoft.doce.api.domain.reports.early_departures.EarlyDeparturesExportable;
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
@RequestMapping("/reports/early-departures")
@RequiredArgsConstructor
public class EarlyDeparturesReportResource {
    private final EarlyDeparturesAndArrivalsJpaReportRepository earlyDeparturesReportRepository;

    @GetMapping
    public Page<?> getEarlyDepartureReport(@RequestParam(name = "date", defaultValue = "") String date,
                                           @RequestParam(name = "group", defaultValue = "") String groupId,
                                           @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                                           @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                                           @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) throws ParseException {

        final Pageable pageable = PageRequest.of(page, size);
        return earlyDeparturesReportRepository.getEarlyDepartures(date, groupId, pageable);
    }

    @PostMapping(value = "/export")
    public ResponseEntity<Resource> export(
            @RequestParam(name = "date", defaultValue = "") String date,
            @RequestParam(name = "group", defaultValue = "") String groupId,
            @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
            @RequestParam(name = "_sort", required = false, defaultValue = "") String sort,
            @RequestBody ExportRequest exportRequest,
            @RequestParam final Map<String, Object> queryMap) throws ParseException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, DocumentException, IOException {

        final Pageable pageable = PageRequest.of(page, 1000);
        Page<EarlyDepartureResponse> responsePage = earlyDeparturesReportRepository.getEarlyDepartures(date, groupId, pageable);

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
        Resource resource = exporter.export(exportable);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                .body(resource);
    }

}
