package com.habilisoft.doce.api.utils.export.domain;

import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;
import com.habilisoft.doce.api.utils.export.dto.FieldValue;

import java.util.List;

public interface Exportable {
    String getCaption();
    List<FieldMetaInfo> getFieldsMetaInfo();
    List<List<FieldValue>> getFieldsValues();
    List<String> getUserFieldsToExport();
    List<UserFilter> getUserFilters();
}
