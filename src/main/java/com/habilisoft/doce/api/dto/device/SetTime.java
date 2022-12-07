package com.habilisoft.doce.api.dto.device;

import com.habilisoft.doce.api.utils.DefaultTimeZone;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2019-06-08.
 */
@Data
public class SetTime extends QueueEvent {

    @JsonProperty(value = "cloudtime")
    private String cloudTime;

    @Builder
    public SetTime(){
        this.setType(Type.SET_TIME);
        SimpleDateFormat sdf =  new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        sdf.setTimeZone(DefaultTimeZone.getDefault());
        this.cloudTime = sdf.format(new Date());
    }
}
