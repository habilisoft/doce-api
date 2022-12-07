package com.habilisoft.doce.api.domain.model;

import com.habilisoft.doce.api.serialization.BaseEnum;

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
