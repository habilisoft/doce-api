package com.habilisoft.doce.api.utils.export.domain;

import com.habilisoft.doce.api.utils.export.dto.ExportRequest;
import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;
import com.habilisoft.doce.api.utils.export.dto.FieldValue;
import com.habilisoft.doce.api.utils.export.utils.ExportableFieldsUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExportableEntity<I, O> implements Exportable {

    private String caption;
    private List<FieldMetaInfo> fieldsMetaInfo;
    private List<String> userFieldsToExport;
    private List<List<FieldValue>> fieldsValues;
    private List<UserFilter> userFilters;

    public ExportableEntity(List<O> records, ExportRequest exportRequest){
        this(exportRequest.getCaption(), records, exportRequest.getUserFieldsToExport(), exportRequest.getUserFilters());
    }

    public ExportableEntity(String caption, List<O> records, List<String> userFieldsToExport) {
        this(caption, records, userFieldsToExport, Collections.emptyList());
    }

    public ExportableEntity(String caption, List<O> records, List<String> userFieldsToExport, List<UserFilter> userFilters) {
        if(caption == null || caption.isEmpty()){
            throw new IllegalArgumentException("Caption not provided");
        }
        if(records == null || records.size() == 0){
            throw new IllegalArgumentException("No records provided to be exported");
        }
        if(userFieldsToExport == null || userFieldsToExport.size() == 0){
            throw new IllegalArgumentException("No fields were provided to be exported");
        }
        this.caption = caption;
        this.userFieldsToExport = userFieldsToExport;
        this.fieldsMetaInfo = ExportableFieldsUtil.getValidatedFieldsToExport(records.get(0).getClass(), userFieldsToExport);
        this.fieldsValues = records.stream().map(this::getRecordFieldsValues).collect(Collectors.toList());
        this.userFilters = userFilters;
    }

    public ExportableEntity(String caption, List<I> records, Mapper<I, O> mapper, List<String> userFieldsToExport) {
        this(caption, records.stream().map(mapper::map).collect(Collectors.toList()), userFieldsToExport);
    }

    @Override
    public String getCaption() {
        return this.caption;
    }

    @Override
    public List<UserFilter> getUserFilters() {
        return userFilters;
    }

    @Override
    public List<FieldMetaInfo> getFieldsMetaInfo() {
        return this.fieldsMetaInfo;
    }

    @Override
    public List<List<FieldValue>> getFieldsValues() {
        return this.fieldsValues;
    }

    @Override
    public List<String> getUserFieldsToExport() {
        return this.userFieldsToExport;
    }

    private List<FieldValue> getRecordFieldsValues(O currentRecord) {
        return getFieldsMetaInfo()
                .stream()
                .map(f -> {
                    if (f.getField() != null) {
                        return FieldValueProcessor.getValue(currentRecord, f.getField());
                    }
                    return FieldValueProcessor.getValue(currentRecord, f.getMethod());
                })
                .collect(Collectors.toList());
    }


}
