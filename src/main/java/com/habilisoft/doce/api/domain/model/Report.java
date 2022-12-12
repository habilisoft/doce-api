package com.habilisoft.doce.api.domain.model;

import com.habilisoft.doce.api.serialization.BaseEnum;

/**
 * Created on 3/12/22.
 */
public enum Report implements BaseEnum {
    WORKED_HOURS("Horas trabajadas"),
    LATE_ARRIVALS("Llegadas tardías"),
    EARLY_DEPARTURES("Salidas anticipadas");
    final String displayName;

    Report(String displayName) {
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
