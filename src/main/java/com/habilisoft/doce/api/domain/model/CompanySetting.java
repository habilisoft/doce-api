package com.habilisoft.doce.api.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.habilisoft.doce.api.serialization.BaseEnum;
import com.habilisoft.doce.api.serialization.BaseEnumConverter;

/**
 * Created on 25/12/22.
 */
@JsonSerialize(converter = BaseEnumConverter.class)
public enum CompanySetting implements BaseEnum {
    PUNCH_POLICY("Punch Policy");

    final String displayName;

    CompanySetting(String displayName) {
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
