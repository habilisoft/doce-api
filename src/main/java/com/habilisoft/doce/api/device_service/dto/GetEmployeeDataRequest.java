package com.habilisoft.doce.api.device_service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created on 3/3/23.
 */
@Data
@Builder
public class GetEmployeeDataRequest {
    private String enrollId;
    private Integer backUpNumber;
    private String deviceSerialNumber;
}
