package com.habilisoft.doce.api.domain.model;

import com.habilisoft.doce.api.serialization.BaseEnum;
import com.habilisoft.doce.api.serialization.BaseEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(converter = BaseEnumConverter.class)
public enum LocationType implements BaseEnum {
    FIXED("Fijo"),
    AMBULATORY("Ambulatorio");

    String displayName;

    LocationType(String displayName) {
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
