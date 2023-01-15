package com.habilisoft.doce.api.reporting.domain;

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
    private String description;
    private String query;
    private List<FilterField> filterFields;
}
