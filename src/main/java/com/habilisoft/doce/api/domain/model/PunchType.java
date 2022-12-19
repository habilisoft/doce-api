package com.habilisoft.doce.api.domain.model;

import com.habilisoft.doce.api.serialization.BaseEnum;
import com.habilisoft.doce.api.serialization.BaseEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(converter = BaseEnumConverter.class)
public enum PunchType implements BaseEnum {
    IN("Entrada"),
    DUPLICATED_IN("Entrada Duplicada"),
    START_BREAK("Inicio de Descanzo"),
    END_BREAK("Fin de descanzo"),
    OUT("Salida"),
    NOT_IN_SCHEDULE("No Programado"),
    NO_WORK_SHIFT("No Tiene Turno Asignado");

    final String displayName;


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
