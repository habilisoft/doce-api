package com.habilisoft.doce.api.reporting.domain.model;

import lombok.Data;

import java.util.Map;

/**
 * Created on 6/1/23.
 */
@Data
public class ReportUIColumn {
    private String header;
    private String field;
    private String columnWidth;
    private String render;
    private Integer displayOrder;
}
