package com.habilisoft.doce.api.reporting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 20/1/23.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportQueryFilter {
    private String uiField;
    private String queryField;
    private Type type;

    public enum  Type {
        DATE
    }
}
