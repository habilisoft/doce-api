package com.armando.timeattendance.api.dto.device;

import com.armando.timeattendance.api.serialization.BaseEnum;
import com.armando.timeattendance.api.serialization.BaseEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;

@JsonSerialize(converter = BaseEnumConverter.class)
public enum EventType implements BaseEnum {

    @SerializedName("0")
    NOT_SELECTED(0, "No seleccionado"),
    UNDEFINED1(1, ""),
    UNDEFINED2(2, ""),
    UNDEFINED3(3, ""),
    UNDEFINED4(4, ""),

    @SerializedName("5")
    IN(5, "Entrada"),

    @SerializedName("6")
    OUT(6, "Salida");

    private final int value;
    private final String displayName;

    public int value() {
        return this.value;
    }

    EventType(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getMessageBundle() {
        return null;
    }
}
