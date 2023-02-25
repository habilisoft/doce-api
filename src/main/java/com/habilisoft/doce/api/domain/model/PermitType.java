package com.habilisoft.doce.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 18/2/23.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermitType {
    private Long id;
    private String description;
}
