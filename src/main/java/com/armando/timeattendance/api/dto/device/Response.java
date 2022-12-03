package com.armando.timeattendance.api.dto.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

/**
 * Created on 2019-04-21.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private String ret;
    private Boolean result;
    private String reason;
    private String cloudtime = "2016-03-25 13:49:30";

    public String toString(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        }catch (Exception e){
            return  null;
        }
    }
}
