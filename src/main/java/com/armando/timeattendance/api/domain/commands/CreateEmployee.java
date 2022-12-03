package com.armando.timeattendance.api.domain.commands;

import com.armando.timeattendance.api.domain.model.LocationType;
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
public class CreateEmployee {
    private String fullName;
    private String documentNumber;
    private String externalId;
    private Long groupId;
    private Long locationId;
    private LocationType locationType;
}
