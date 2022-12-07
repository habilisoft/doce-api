package com.habilisoft.doce.api.serialization;

import com.habilisoft.doce.api.dto.device.QueueEvent;
import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * Created on 2019-05-07.
 */
public class CommandTypeConverter extends StdConverter<String, QueueEvent.Type> {

    @Override
    public QueueEvent.Type convert(String value) {

        return QueueEvent.Type.getByValue(value);

    }
}
