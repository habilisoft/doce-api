package com.habilisoft.doce.api.reporting.export;

import com.habilisoft.doce.api.reporting.domain.model.Report;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created on 21/1/23.
 */
@Data
@Builder
public class ExportableImpl implements Exportable {
    private List<FieldMetaInfo> fieldsMetaInfo;
    private List<Map<String, Object>> records;
    private List<String> userFieldsToExport;
    private List<UserFilter> userFilters;
    private Report report;
}
