package com.habilisoft.doce.api.reporting.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 6/1/23.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private Long id;
    private String name;
    private String slug;
    private String description;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String query;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String defaultOrder;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String countQuery;
    private List<ReportUIFilter> uiFilters;
    private List<ReportUIColumn> uiColumns;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<ReportQueryFilter> queryFilters;
    private Boolean inlineQueryParams;


    public Report(String query, String countQuery) {
        this.query = query;
        this.countQuery = countQuery;
    }
}
