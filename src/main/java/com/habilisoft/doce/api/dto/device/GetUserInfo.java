package com.habilisoft.doce.api.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class GetUserInfo extends QueueEvent {
    @JsonProperty(value = "enrollid")
    private Integer enrollId;

    @JsonProperty(value = "backupnum")
    private Integer backupNum;

    @Builder
    public GetUserInfo(Integer enrollId, Integer backupNum) {
        this.enrollId = enrollId;
        this.backupNum = backupNum;
        this.type = Type.GET_USER_INFO;
    }
}
