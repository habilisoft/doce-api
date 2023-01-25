package com.habilisoft.doce.api.web.reports;

import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.domain.model.ReportSearchRequest;
import com.habilisoft.doce.api.reporting.domain.repositories.ReportRepository;
import com.habilisoft.doce.api.reporting.export.ExportRequest;
import com.habilisoft.doce.api.reporting.export.MailReportRequest;
import com.habilisoft.doce.api.web.reports.domain.services.ReportExportService;
import com.habilisoft.doce.api.web.reports.domain.services.ReportMailService;
import com.habilisoft.doce.api.web.reports.domain.services.ReportSearchService;
import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created on 15/1/23.
 */
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportsResource {
    private final ReportRepository repository;
    private final ReportSearchService service;
    private final ReportExportService exportService;
    private final ReportMailService mailService;

    @PostMapping
    ResponseEntity<?> create(@RequestBody Report report) {
        repository.save(report);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{reportId}")
    ResponseEntity<?> edit(@PathVariable Long reportId, @RequestBody Report report) {
        report.setId(reportId);
        repository.save(report);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<?> list() {
        return ResponseEntity.ok(repository.list());
    }

    @GetMapping("/{reportSlug}")
    Page<?> search(@PathVariable String reportSlug,
                   @RequestParam final Map<String, Object> queryMap,
                   @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                   @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size) {

        queryMap.remove("_page");
        queryMap.remove("_size");
        return service.search(
                ReportSearchRequest
                        .builder()
                        .reportSlug(reportSlug)
                        .page(page)
                        .size(size)
                        .queryMap(queryMap)
                        .build());
    }

    @PostMapping("/{reportSlug}/export")
    ResponseEntity<Resource> export(@PathVariable String reportSlug,
                                    @RequestBody ExportRequest exportRequest) throws CsvRequiredFieldEmptyException, DocumentException, CsvDataTypeMismatchException, IOException {
        Resource resource = exportService.export(reportSlug, exportRequest);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                .body(resource);
    }

    @PostMapping("/{reportSlug}/export/send-mail")
    ResponseEntity<?> sendMail(@PathVariable String reportSlug,
                               @RequestBody MailReportRequest request) throws CsvRequiredFieldEmptyException, DocumentException, CsvDataTypeMismatchException, IOException {
        mailService.sendReport(reportSlug, request);
        return ResponseEntity.ok().build();
    }

}
