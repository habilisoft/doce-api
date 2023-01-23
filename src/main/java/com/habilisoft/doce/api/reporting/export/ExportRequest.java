package com.habilisoft.doce.api.reporting.export;

import com.habilisoft.doce.api.reporting.export.enums.ExportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportRequest {
    private String caption;
    private ExportType exportType;
    private List<String> userFieldsToExport;
    private List<UserFilter> userFilters;
    private Map<String, String> params;
}