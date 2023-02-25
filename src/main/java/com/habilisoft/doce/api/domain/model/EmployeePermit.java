package com.habilisoft.doce.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created on 18/2/23.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePermit {
    private Long id;
    private Employee employee;
    private Date fromDate;
    private Date toDate;
    private PermitType permitType;
    private String comment;
}
