package com.habilisoft.doce.api.reporting.export;

import com.habilisoft.doce.api.reporting.domain.model.Report;

import java.util.List;
import java.util.Map;

public interface Exportable {
    List<Map<String, Object>> getRecords();
    List<UserFilter> getUserFilters();
    Report getReport();
}
