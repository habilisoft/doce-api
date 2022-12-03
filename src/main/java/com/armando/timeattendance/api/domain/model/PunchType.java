package com.armando.timeattendance.api.domain.model;

import com.armando.timeattendance.api.serialization.BaseEnum;
import com.armando.timeattendance.api.serialization.BaseEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(converter = BaseEnumConverter.class)
public enum PunchType implements BaseEnum {
    IN("Entrada"),
    START_BREAK("Inicio de Descanzo"),
    END_BREAK("Fin de descanzo"),
    OUT("Salida");

    String displayName;


    PunchType(String displayName) {
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
