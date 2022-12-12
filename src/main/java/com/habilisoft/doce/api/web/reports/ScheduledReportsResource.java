package com.habilisoft.doce.api.web.reports;

import com.habilisoft.doce.api.auth.base.utils.SortUtils;
import com.habilisoft.doce.api.persistence.entities.DeviceEntity;
import com.habilisoft.doce.api.persistence.scheduler.entitites.ScheduledReportEntity;
import com.habilisoft.doce.api.persistence.scheduler.services.ScheduledReportSearchService;
import com.habilisoft.doce.api.scheduler.convertes.ScheduledReportJpaConverter;
import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.services.ScheduledReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created on 7/12/22.
 */
@RestController
@RequestMapping("/scheduled-reports")
@RequiredArgsConstructor
public class ScheduledReportsResource {
    private final ScheduledReportService service;
    private final ScheduledReportSearchService searchService;
    private final ScheduledReportJpaConverter converter;

    @PostMapping
    ResponseEntity<?> create(@RequestBody ScheduledReport scheduledReport) {
        service.save(scheduledReport);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{scheduledReportId}")
    ResponseEntity<?> edit(@RequestBody ScheduledReport scheduledReport,
                           @PathVariable Long scheduledReportId) {
        service.update(scheduledReportId, scheduledReport);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<?> search(@RequestParam final Map<String, Object> queryMap,
                          @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                          @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                          @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {

        if (!StringUtils.hasLength(sort))
            sort = "-" + "createdDate";

        Page<ScheduledReportEntity> entityPage = searchService.search(
                queryMap,
                PageRequest.of(
                        page,
                        size,
                        SortUtils.processSort(sort, new String[]{"createdDate"})
                )
        );

        return entityPage.map(converter::fromJpaEntity);
    }
}
