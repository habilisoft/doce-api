package com.habilisoft.doce.api.reporting.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Created on 15/1/23.
 */
@Data
@Builder
public class ReportSearchRequest {
    private String reportSlug;
    private Map<String, Object> queryMap;
    private Integer page;
    private Integer size;
}
