package com.armando.timeattendance.api.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNameDto {
    @JsonProperty("enrollid")
    private Integer enrollId;
    private String name;
}
