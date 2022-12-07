package com.habilisoft.doce.api.web.reports;

import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.services.ScheduledReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 7/12/22.
 */
@RestController
@RequestMapping("/scheduled-reports")
@RequiredArgsConstructor
public class ScheduledReportsResource {
    private final ScheduledReportService service;

    @PostMapping
    ResponseEntity<?> create(@RequestBody ScheduledReport scheduledReport) {
        service.save(scheduledReport);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{scheduledReportId}")
    ResponseEntity<?> edit(@RequestBody ScheduledReport scheduledReport,
                           @PathVariable Long scheduledReportId) {
        service.update(scheduledReportId, scheduledReport);
        return ResponseEntity.ok().build();
    }
}
