package com.habilisoft.doce.api.dto.device;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VerifyMode {
    FINGER_PRINT_CARD_PASSWORD,
    CARD_AND_FINGER_PRINT,
    PASSWORD_AND_FINGER_PRINT,
    CARD_AND_FINGER_PRINT_AND_PASSWORD,
    CARD_AND_PASSWORD;

    @JsonValue
    public int value() {
        return this.ordinal();
    }
}
