package com.habilisoft.doce.api.utils.export;

import com.habilisoft.doce.api.domain.util.DateUtil;
import com.habilisoft.doce.api.persistence.mapping.AmbulatoryAssistanceSummaryReport;
import com.habilisoft.doce.api.utils.export.domain.Exportable;
import com.habilisoft.doce.api.utils.export.domain.UserFilter;
import com.habilisoft.doce.api.utils.export.dto.ExportRequest;
import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;
import com.habilisoft.doce.api.utils.export.dto.FieldValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AmbulatoryAssistanceSummaryExportable implements Exportable {

    private String caption;
    private List<Map> records;
    private List<List<FieldValue>> fieldsValuesList;
    private List<String> userFieldsToExport;
    private String filterType;
    private List<FieldMetaInfo> fieldsMetaInfo;
    private List<UserFilter> userFilters;

    public AmbulatoryAssistanceSummaryExportable(List<Map> records, ExportRequest exportRequest, String filterType) {
        this(exportRequest.getCaption(), records, exportRequest.getUserFieldsToExport(), filterType, exportRequest.getUserFilters());
    }

    public AmbulatoryAssistanceSummaryExportable(String caption, List<Map> records, List<String> userFieldsToExport, String filterType) {
        this(caption, records, userFieldsToExport, filterType, Collections.emptyList());
    }

    public AmbulatoryAssistanceSummaryExportable(String caption, List<Map> records, List<String> userFieldsToExport, String filterType, List<UserFilter> userFilters) {
        init(caption, records, userFieldsToExport, filterType, userFilters);
    }

    private void init(String caption, List<Map> records, List<String> userFieldsToExport, String filterType, List<UserFilter> userFilters) {
        this.caption = caption;
        this.records = records;
        this.userFieldsToExport = userFieldsToExport;
        this.filterType = filterType;
        setFieldsMetaInfo();
        setFieldsValuesList();
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public List<UserFilter> getUserFilters() {
        return userFilters;
    }

    private void setFieldsValuesList() {
        fieldsValuesList = records.stream().map(r -> {
            List<AmbulatoryAssistanceSummaryReport> visits = ((List<AmbulatoryAssistanceSummaryReport>) r.get("visits"));
            return new ArrayList<FieldValue>() {{
                add(new FieldValue("id", r.get("id").toString()));
                add(new FieldValue("name", r.get("name").toString()));
                addAll(buildEmployeesWorkHours(visits));
            }};
        }).collect(Collectors.toList());
    }

    private List<FieldValue> buildEmployeesWorkHours(List<AmbulatoryAssistanceSummaryReport> visits) {
        return visits.stream().map(visit -> new FieldValue(DateUtil.getMMDDYYYY(visit.getRecordDate()), String.valueOf(visit.getVisits()))).collect(Collectors.toList());
    }

    private void setFieldsMetaInfo() {
        fieldsMetaInfo = new ArrayList<>() {{
            add(new FieldMetaInfo("id", "ID"));
            add(new FieldMetaInfo("name", "Nombre"));
            addAll(buildDateColumns());
        }};
    }

    private List<FieldMetaInfo> buildDateColumns() {
        return ((List<AmbulatoryAssistanceSummaryReport>) records.get(0).get("visits"))
                .stream()
                .map(e -> {
                    String date = DateUtil.getMMDDYYYY(e.getRecordDate());
                    userFieldsToExport.add(date);
                    String dateDesc = filterType.equals("week") ? DateUtil.getDayName(e.getRecordDate()) : DateUtil.getMMMDD(e.getRecordDate());
                    return new FieldMetaInfo(date, dateDesc);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<FieldMetaInfo> getFieldsMetaInfo() {
        return fieldsMetaInfo;
    }

    @Override
    public List<List<FieldValue>> getFieldsValues() {
        return fieldsValuesList;
    }

    @Override
    public List<String> getUserFieldsToExport() {
        return userFieldsToExport;
    }
}
