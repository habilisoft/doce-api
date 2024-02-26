package com.habilisoft.doce.api.device_service.dto;

import lombok.Data;

/**
 * Created on 3/3/23.
 */
@Data
public class EmployeeDataResponse {
    private String ret;
    private String name;
    private Boolean result;
    private String enrollid;
    private Integer backupnum;
    private String record;
    private Integer admin;
}
