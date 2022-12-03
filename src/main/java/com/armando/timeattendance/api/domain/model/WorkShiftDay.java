package com.armando.timeattendance.api.domain.model;

import com.armando.timeattendance.api.serialization.BaseEnum;
import com.armando.timeattendance.api.serialization.BaseEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public enum WorkShiftDay implements BaseEnum {
    MONDAY("Lunes"),
    TUESDAY("Martes"),
    WEDNESDAY("Miercoles"),
    THURSDAY("Jueves"),
    FRIDAY("Viernes"),
    SATURDAY("Sabado"),
    SUNDAY("Domingo");

    final String displayName;

    WorkShiftDay(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getMessageBundle() {
        return  "messages.Enumns";
    }


}
