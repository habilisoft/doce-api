package com.habilisoft.doce.api.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomJsonTimeDeserializerWithoutTimeZone extends JsonDeserializer<LocalTime> {
    @Override
    public LocalTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return LocalTime.parse(jp.getValueAsString(), DateTimeFormatter.ofPattern("H:mm"));
    }
}
