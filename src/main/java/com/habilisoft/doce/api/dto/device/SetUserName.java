package com.habilisoft.doce.api.dto.device;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SetUserName extends QueueEvent {
    private Integer count;
    private List<UserNameDto> record;

    @Builder
    public SetUserName(Integer count, List<UserNameDto> record) {
        this.count = count;
        this.record = record;
        this.type = Type.SET_USER_NAME;
    }
}
