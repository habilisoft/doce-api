package com.habilisoft.doce.api.domain.reports.late_arrivals;

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

public class LateDeparturesExportable implements Exportable {

    private String caption;
    private List<Map<String, String>> records;
    private List<List<FieldValue>> fieldsValuesList;
    private List<String> userFieldsToExport;
    private List<FieldMetaInfo> fieldsMetaInfo;
    private List<UserFilter> userFilters;

    public LateDeparturesExportable(List<Map<String, String>> records, ExportRequest exportRequest, String filterType){
        this(exportRequest.getCaption(), records, exportRequest.getUserFieldsToExport(), filterType, exportRequest.getUserFilters());
    }

    public LateDeparturesExportable(String caption, List<Map<String, String>> records, List<String> userFieldsToExport, String filterType) {
        this(caption, records, userFieldsToExport, filterType, Collections.emptyList());
    }

    public LateDeparturesExportable(String caption, List<Map<String, String>> records, List<String> userFieldsToExport, String filterType, List<UserFilter> userFilters) {
        init(caption, records, userFieldsToExport, filterType, userFilters);
    }

    private void init(String caption, List<Map<String, String>> records, List<String> userFieldsToExport, String filterType, List<UserFilter> userFilters) {
        this.caption = caption;
        this.records = records;
        this.userFieldsToExport = userFieldsToExport;
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
        fieldsValuesList = records.stream().map(r -> new ArrayList<FieldValue>() {
            {
            add(new FieldValue("enrollId", r.get("enrollId").toString()));
            add(new FieldValue("employeeName", r.get("employeeName").toString()));
            add(new FieldValue("group", r.get("group").toString()));
            add(new FieldValue("date", r.get("date").toString()));
            add(new FieldValue("hour", r.get("hour").toString()));
            add(new FieldValue("difference", r.get("difference").toString()));

        }}).collect(Collectors.toList());
    }

    private void setFieldsMetaInfo() {
        fieldsMetaInfo = new ArrayList<>() {{
            add(new FieldMetaInfo("enrollId", "Código"));
            add(new FieldMetaInfo("employeeName", "Nombre"));
            add(new FieldMetaInfo("group", "Grupo"));
            add(new FieldMetaInfo("date", "Fecha"));
            add(new FieldMetaInfo("hour", "Hora de Entrada"));
            add(new FieldMetaInfo("difference", "Tiempo de Atraso"));
        }};
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
