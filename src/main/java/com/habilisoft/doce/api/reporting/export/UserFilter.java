package com.habilisoft.doce.api.reporting.export;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {
    private String field;
    private String value;
    private String displayName;
    private String displayValue;
}
