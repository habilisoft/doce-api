package com.armando.timeattendance.api.serialization;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Map;

public class BaseEnumConverter extends StdConverter<BaseEnum, Map<String, Object>> {
    @Override
    public Map<String, Object> convert(BaseEnum baseEnum) {
        return Map.of(
                "name", baseEnum.name(),
                "displayName", baseEnum.getDisplayName(),
                "value", baseEnum.getValue());
    }
}
