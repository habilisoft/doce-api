package com.habilisoft.doce.api.reporting.domain;

import lombok.Data;

import java.util.Map;

/**
 * Created on 6/1/23.
 */
@Data
public class FilterField {
    private Integer displayOrder;
    private String displayName;
    private String field;
    private Type type;
    private Map<String, Object> metadata;

    public enum Type {
        SELECT, DATE
    }
}
