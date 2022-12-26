package com.habilisoft.doce.api.web;

import com.habilisoft.doce.api.auth.base.resources.BaseResource;
import com.habilisoft.doce.api.auth.base.utils.SortUtils;
import com.habilisoft.doce.api.domain.services.TimeAttendanceRecordService;
import com.habilisoft.doce.api.persistence.entities.TimeAttendanceRecordEntity;
import com.habilisoft.doce.api.persistence.mapping.TimeAttendanceRecordReport;
import com.habilisoft.doce.api.persistence.services.TimeAttendanceRecordSearchService;
import com.habilisoft.doce.api.utils.export.EmployeesWorkHoursExportable;
import com.habilisoft.doce.api.utils.export.domain.Exportable;
import com.habilisoft.doce.api.utils.export.domain.ExportableEntity;
import com.habilisoft.doce.api.utils.export.domain.Exporter;
import com.habilisoft.doce.api.utils.export.domain.ExporterFactory;
import com.habilisoft.doce.api.utils.export.dto.ExportRequest;
import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;
import com.habilisoft.doce.api.utils.export.utils.ExportableFieldsUtil;
import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/time-attendance-records")
@RequiredArgsConstructor
public class TimeAttendanceRecordResource extends BaseResource<TimeAttendanceRecordEntity, Long> {
    private final TimeAttendanceRecordService service;
    private final TimeAttendanceRecordSearchService searchService;

    @RequestMapping(method = RequestMethod.GET, value = "/employee-attendance-report")
    public Page<?> getEmployeeAttendanceReport(@RequestParam final Map<String, Object> queryMap,
                                                      @RequestParam(name = "_page", required = false, defaultValue = DEFAULT_PAGE) final Integer page,
                                                      @RequestParam(name = "_size", required = false, defaultValue = PAGE_SIZE) final Integer size,
                                                      @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) throws ParseException {

        final Pageable pageable = PageRequest.of(page, size, SortUtils.processSort("-recordDate,-employee.id", this.getSortableFields()));
        return this.service.getEmployeeAttendanceReport(queryMap, pageable);
    }


    @RequestMapping(value = "/employee-attendance-report/export", method = RequestMethod.POST)
    public ResponseEntity<Resource> exportTimeAttendanceRecordReport(
            @RequestBody ExportRequest exportRequest,
            @RequestParam final Map<String, Object> queryMap) throws IOException, DocumentException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, ParseException {
        final Pageable pageable = PageRequest.of(0, 2000, SortUtils.processSort("-recordDate,-employee.id", this.getSortableFields()));
        Page<TimeAttendanceRecordReport> page = this.service.getEmployeeAttendanceReport(queryMap, pageable);
        Exporter exporter = ExporterFactory.getInstance(exportRequest.getExportType());
        ExportableEntity<TimeAttendanceRecordReport, TimeAttendanceRecordReport> exportableEntity = new ExportableEntity<>( page.getContent(), exportRequest);
        Resource resource = exporter.export(exportableEntity);
        return ResponseEntity
                .ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                .body(resource);
    }

    @RequestMapping(value = "/employee-attendance-report/exportable-fields", method = RequestMethod.GET)
    public ResponseEntity<List<FieldMetaInfo>> getTimeAttendanceRecordReportExportableFields() {
        return ResponseEntity.ok(ExportableFieldsUtil.getFieldsMetaInfo(TimeAttendanceRecordReport.class));
    }


    @RequestMapping(method = RequestMethod.GET, value = "/employee-attendance-summary")
    public ResponseEntity getEmployeeTimeAttendanceSummary(@RequestParam final Map<String, Object> queryMap) throws ParseException {
        return ResponseEntity.ok(this.service.getEmployeeTimeAttendanceSummary(queryMap));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employee-attendance-report-details")
    public ResponseEntity getEmployeeTimeAttendanceSummaryDetail(@RequestParam final Map<String, Object> queryMap,
                                                                 @RequestParam(name = "_page", required = false, defaultValue = DEFAULT_PAGE) final Integer page,
                                                                 @RequestParam(name = "_size", required = false, defaultValue = PAGE_SIZE) final Integer size,
                                                                 @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) throws ParseException {

        final Pageable pageable = PageRequest.of(page, size, SortUtils.processSort("-employee.id", this.getSortableFields()));
        return ResponseEntity.ok(
                this.service.getTimeAttendanceSummaryDetail(queryMap, pageable)
        );
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employee-work-hours-report")
    public ResponseEntity getEmployeeWorkHourReport(@RequestParam final Map<String, Object> queryMap,
                                                    @RequestParam(name = "_page", required = false, defaultValue = DEFAULT_PAGE) final Integer page,
                                                    @RequestParam(name = "_size", required = false, defaultValue = PAGE_SIZE) final Integer size,
                                                    @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) throws ParseException {
        return ResponseEntity.ok(
                this.service.getEmployeeWorkHourByRange(queryMap)
        );
    }

    @RequestMapping(value = "/employee-work-hours-report/export", method = RequestMethod.POST)
    public ResponseEntity<Resource> export(
            @RequestBody ExportRequest exportRequest,
            @RequestParam final Map<String, Object> queryMap) throws ParseException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, DocumentException, IOException {
        final String filterType = queryMap.containsKey("filterType") ? queryMap.get("filterType").toString() : "week";
        List<Map> records = this.service.getEmployeeWorkHourByRange(queryMap);
        Exportable exportable = new EmployeesWorkHoursExportable(records, exportRequest, filterType);
        Exporter exporter = ExporterFactory.getInstance(exportRequest.getExportType());
        Resource resource = exporter.export(exportable);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                .body(resource);
    }


    @RequestMapping(value = "/employee-work-hours-report/exportable-fields", method = RequestMethod.GET)
    public ResponseEntity<List<FieldMetaInfo>> getExportableFields() {
        return ResponseEntity.ok(new ArrayList<>(){{
            add(new FieldMetaInfo("id","ID"));
            add(new FieldMetaInfo("name","Nombre"));
            add(new FieldMetaInfo("totalWorkTime","Total de Horas"));
        }});
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employee-work-hours-report/{employeeId}/{recordDate}/details")
    public ResponseEntity getEmployeeWorkHourDetail(@PathVariable("employeeId") final Long employeeId,
                                                    @PathVariable("recordDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date recordDate,
                                                    @RequestParam(name = "_page", required = false, defaultValue = DEFAULT_PAGE) final Integer page,
                                                    @RequestParam(name = "_size", required = false, defaultValue = PAGE_SIZE) final Integer size,
                                                    @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {

        final Pageable pageable = PageRequest.of(page, size, SortUtils.processSort("-employee.id", this.getSortableFields()));
        return ResponseEntity.ok(this.service.getEmployeeWorkHourDetails(employeeId, recordDate, pageable));
    }


    /*@RequestMapping(method = RequestMethod.GET, value = "/ambulatory-employees-assistance-report")
    public ResponseEntity getAmbulatoryEmployeeAssistanceReport(@RequestParam final Map<String, Object> queryMap,
                                                                @RequestParam(name = "_page", required = false, defaultValue = DEFAULT_PAGE) final Integer page,
                                                                @RequestParam(name = "_size", required = false, defaultValue = PAGE_SIZE) final Integer size,
                                                                @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) throws ParseException {

        return ResponseEntity.ok(
                this.service.getAmbulatoryEmployeeAssistanceByRange(queryMap)
        );
    }*/


    /*@RequestMapping(value = "/ambulatory-employees-assistance-report/export", method = RequestMethod.POST)
    public ResponseEntity<Resource> ambulatoryEmployeeAssistanceReportExport(
            @RequestBody ExportRequest exportRequest,
            @RequestParam final Map<String, Object> queryMap) throws ParseException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, DocumentException, IOException {
        final String filterType = queryMap.containsKey("filterType") ? queryMap.get("filterType").toString() : "week";
        List<Map> records = this.service.getAmbulatoryEmployeeAssistanceByRange(queryMap);
        Exportable exportable = new AmbulatoryAssistanceSummaryExportable(records, exportRequest, filterType);
        Exporter exporter = ExporterFactory.getInstance(exportRequest.getExportType());
        Resource resource = exporter.export(exportable);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                .body(resource);
    }*/


    @RequestMapping(value = "/ambulatory-employees-assistance-report/exportable-fields", method = RequestMethod.GET)
    public ResponseEntity<List<FieldMetaInfo>> getAmbulatoryEmployeeAssistanceReportExportableFields() {
        return ResponseEntity.ok(new ArrayList<>(){{
            add(new FieldMetaInfo("id","ID"));
            add(new FieldMetaInfo("name","Nombre"));
        }});
    }

    /*@RequestMapping(method = RequestMethod.GET, value = "/ambulatory-employees-assistance-report/{employeeId}/{recordDate}/details")
    public ResponseEntity getEmployeeTimeAttendanceSummary(@PathVariable("employeeId") final Long employeeId,
                                                           @PathVariable("recordDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date recordDate,
                                                           @RequestParam(name = "_page", required = false, defaultValue = DEFAULT_PAGE) final Integer page,
                                                           @RequestParam(name = "_size", required = false, defaultValue = PAGE_SIZE) final Integer size,
                                                           @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {

        final Pageable pageable = PageRequest.of(page, size, SortUtils.processSort("-employeeId", this.getSortableFields()));
        return ResponseEntity.ok(this.service.getAmbulatoryEmployeeAssistanceDetails(employeeId, recordDate, pageable));
    }*/

    @Override
    public TimeAttendanceRecordSearchService getService() {
        return this.searchService;
    }

    @Override
    public String[] getSortableFields() {
        return new String[]{"time","recordDate", "lastModifiedDate", "employee.id"};
    }
}
