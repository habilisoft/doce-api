package com.habilisoft.doce.api.domain.model.punch.policy;

import com.habilisoft.doce.api.serialization.BaseEnum;

/**
 * Created on 25/12/22.
 */
public enum PunchPolicyType implements BaseEnum {
    LAST_PUNCH_IS_OUT("Último ponche es salida"),
    IN_TIME_RANGE("Rango tiempo de entrada");

    final String displayName;

    PunchPolicyType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getMessageBundle() {
        return "messages";
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
