package com.armando.timeattendance.api.serialization;

import com.armando.timeattendance.api.dto.device.QueueEvent;
import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * Created on 2019-05-07.
 */
public class CommandTypeToStringConverter extends StdConverter<QueueEvent.Type, String> {

    @Override
    public String convert(QueueEvent.Type command) {

        return command.value();

    }
}
