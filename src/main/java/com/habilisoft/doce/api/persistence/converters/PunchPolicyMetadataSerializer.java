package com.habilisoft.doce.api.persistence.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created on 25/12/22.
 */
public class PunchPolicyMetadataSerializer extends JsonSerializer<LinkedHashMap<String, Object>> {
    @Override
    public void serialize(LinkedHashMap<String, Object> stringObjectLinkedHashMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        System.out.println(serializerProvider);
    }
}
