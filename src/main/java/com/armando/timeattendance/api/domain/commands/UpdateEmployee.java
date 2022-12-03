package com.armando.timeattendance.api.domain.commands;

import com.armando.timeattendance.api.domain.model.Group;
import com.armando.timeattendance.api.domain.model.Location;
import com.armando.timeattendance.api.domain.model.LocationType;
import com.armando.timeattendance.api.domain.model.WorkShift;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 11/11/22.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateEmployee {
    private Long id;
    private String fullName;
    private String documentNumber;
    private String externalId;
    private Group group;
    private Location location;
    private WorkShift workShift;
}
