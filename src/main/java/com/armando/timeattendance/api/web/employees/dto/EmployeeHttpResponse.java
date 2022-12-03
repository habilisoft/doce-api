package com.armando.timeattendance.api.web.employees.dto;

import com.armando.timeattendance.api.domain.model.Group;
import com.armando.timeattendance.api.domain.model.Location;
import com.armando.timeattendance.api.domain.model.LocationType;
import com.armando.timeattendance.api.domain.model.WorkShift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 29/11/22.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeHttpResponse {
    private Long id;
    private String fullName;
    private Integer enrollId;
    private String externalId;
    private String documentNumber;
    private Location location;
    private Boolean hasFingerPrint;
    private LocationType locationType;
    private WorkShift workShift;
    private Group group;
}
