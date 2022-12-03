package com.armando.timeattendance.api.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created on 28/11/22.
 */
@Data
@Builder
public class ImportEmployeeRequest {
    private Long line;
    private String externalId;
    private String name;
    private String documentNumber;
    private String groupName;
    private String locationName;
    private String workShiftName;
    private String locationType;
}
