package com.habilisoft.doce.api.utils.export.dto;

import com.habilisoft.doce.api.utils.export.domain.UserFilter;
import com.habilisoft.doce.api.utils.export.enums.ExportType;
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
