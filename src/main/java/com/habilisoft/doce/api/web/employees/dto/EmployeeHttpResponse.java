package com.habilisoft.doce.api.web.employees.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.domain.model.Location;
import com.habilisoft.doce.api.domain.model.LocationType;
import com.habilisoft.doce.api.domain.model.WorkShift;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private Boolean active;
}
