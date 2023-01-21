package com.habilisoft.doce.api.utils.export.domain;

import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;
import com.habilisoft.doce.api.utils.export.dto.FieldValue;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created on 21/1/23.
 */
@Data
@Builder
public class ExportableImpl implements Exportable {
    private String caption;
    private List<FieldMetaInfo> fieldsMetaInfo;
    private List<List<FieldValue>> fieldsValues;
    private List<String> userFieldsToExport;
    private List<UserFilter> userFilters;
}
