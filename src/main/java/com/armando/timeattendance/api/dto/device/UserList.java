package com.armando.timeattendance.api.dto.device;

import lombok.Builder;
import lombok.Data;

/**
 * Created on 2019-05-13.
 */
@Data
public class UserList extends QueueEvent {

    @Builder
    public UserList() {
        setType(Type.GET_USER_LIST);
    }

}
