package com.habilisoft.doce.api.utils.export;

import com.habilisoft.doce.api.domain.util.DateUtil;
import com.habilisoft.doce.api.persistence.mapping.EmployeesWorkHourReport;
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

public class EmployeesWorkHoursExportable implements Exportable {

    private String caption;
    private List<Map> records;
    private List<List<FieldValue>> fieldsValuesList;
    private List<String> userFieldsToExport;
    private String filterType;
    private List<FieldMetaInfo> fieldsMetaInfo;
    private List<UserFilter> userFilters;

    public EmployeesWorkHoursExportable(List<Map> records, ExportRequest exportRequest, String filterType){
        this(exportRequest.getCaption(), records, exportRequest.getUserFieldsToExport(), filterType, exportRequest.getUserFilters());
    }

    public EmployeesWorkHoursExportable(String caption,List<Map> records, List<String> userFieldsToExport, String filterType) {
        this(caption, records, userFieldsToExport, filterType, Collections.emptyList());
    }

    public EmployeesWorkHoursExportable(String caption,List<Map> records, List<String> userFieldsToExport, String filterType, List<UserFilter> userFilters) {
        init(caption, records, userFieldsToExport, filterType, userFilters);
    }

    private void init(String caption, List<Map> records, List<String> userFieldsToExport, String filterType, List<UserFilter> userFilters) {
        this.caption = caption;
        this.records = records;
        this.userFieldsToExport = userFieldsToExport;
        this.filterType = filterType;
        this.userFilters = userFilters;
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

    private void setFieldsValuesList(){
        fieldsValuesList = records.stream().map(r -> {
            List<EmployeesWorkHourReport> employeesWorkHours = ((List<EmployeesWorkHourReport>) r.get("workHours"));
            return new ArrayList<FieldValue>() {{
                add(new FieldValue("id", r.get("id").toString()));
                add(new FieldValue("name", r.get("name").toString()));
                add(new FieldValue("totalWorkTime", employeesWorkHours.get(0).getTotalWorkTime()));
                addAll(buildEmployeesWorkHours(employeesWorkHours));
            }};
        }).collect(Collectors.toList());
    }

    private List<FieldValue> buildEmployeesWorkHours(List<EmployeesWorkHourReport> employeesWorkHours){
        return employeesWorkHours.stream().map(w -> {
            String date = DateUtil.getMMDDYYYY(w.getRecordDate());
            String time = "00:00:00";
            if (w.getWorkTime() != null) {
                time = DateUtil.getTime24H(w.getWorkTime());
            }
            return new FieldValue(date, time);
        }).collect(Collectors.toList());
    }

    private void setFieldsMetaInfo() {
        fieldsMetaInfo = new ArrayList<>() {{
            add(new FieldMetaInfo("id", "ID"));
            add(new FieldMetaInfo("name", "Nombre"));
            add(new FieldMetaInfo("totalWorkTime", "Total De Horas"));
            addAll(buildDateColumns());
        }};
    }

    private List<FieldMetaInfo> buildDateColumns() {
        return ((List<EmployeesWorkHourReport>) records.get(0).get("workHours"))
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
