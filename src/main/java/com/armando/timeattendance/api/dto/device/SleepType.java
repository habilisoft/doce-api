package com.armando.timeattendance.api.dto.device;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SleepType {
    NO_SLEEP,
    SLEEP;

    @JsonValue
    public int value() {
        return this.ordinal();
    }
}
