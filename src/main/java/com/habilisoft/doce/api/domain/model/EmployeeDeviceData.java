package com.habilisoft.doce.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 14/1/23.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDeviceData {
    private Long id;
    private Employee employee;
    private Integer number;
    private String record;
    private String deviceModel;
}
