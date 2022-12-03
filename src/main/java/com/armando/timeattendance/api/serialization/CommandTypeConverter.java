package com.armando.timeattendance.api.serialization;

import com.armando.timeattendance.api.dto.device.QueueEvent;
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
