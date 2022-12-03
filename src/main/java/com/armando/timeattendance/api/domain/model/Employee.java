package com.armando.timeattendance.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 8/21/22.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;
    private String fullName;
    private Integer enrollId;
    private String externalId;
    private String documentNumber;
    private Location location;
    private String fingerprintData;
    private LocationType locationType;
    private WorkShift workShift;
    private Group group;
}
