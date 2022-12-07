package com.habilisoft.doce.api.web.workshifts.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created on 29/11/22.
 */
@Data
@Builder
public class WorkShiftHttpResponse {
    private Long id;
    private String name;
}
