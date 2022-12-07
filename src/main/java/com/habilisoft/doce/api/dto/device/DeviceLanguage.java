package com.habilisoft.doce.api.dto.device;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DeviceLanguage {
    ENGLISH(0),
    SIMPLIFIED_CHINESSE(1),
    TAIWAN_CHINESSES(2),
    JAPANESES(3),
    NORTH_KOREAN(4),
    SOUTH_KOREAN(5),
    THAI(6),
    INDONESIAN(7),
    VIETNAMESE(8),
    SPANISH(9),
    FRENCH(10),
    PORTUGUESE(11),
    GERMAN(12),
    RUSSIAN(13),
    TURKISK(14),
    ITALIAN(15),
    CZECH(16),
    ARABIC(17),
    FARSI(18);

    private final Integer value;

    @JsonValue
    public Integer value() {
        return this.value;
    }

    DeviceLanguage(Integer value) {
        this.value = value;
    }
}
