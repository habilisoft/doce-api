package com.habilisoft.doce.api.dto.device;

import com.habilisoft.doce.api.serialization.BaseEnum;
import com.habilisoft.doce.api.serialization.BaseEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 2019-04-21.
 */
@JsonSerialize(converter = BaseEnumConverter.class)
public enum LogRecordMode implements BaseEnum {

    @SerializedName("0")
    UNDEFINED(0, "Indefinido"),

    @SerializedName("1")
    FINGER_PRINT(1, "Huella Dactilar"),

    @SerializedName("2")
    CARD(2, "Tarjeta"),

    @SerializedName("3")
    PASSWORD(3, "Contraseña");

    private final int value;
    private final String displayName;

    public int value() {
        return this.value;
    }

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

    LogRecordMode(int value, String displayName){
        this.value = value;
        this.displayName = displayName;
    }
}
