package com.armando.timeattendance.api.utils;

import java.util.TimeZone;

public class DefaultTimeZone {

    public static TimeZone getDefault() {
        return TimeZone.getTimeZone("America/Santo_Domingo");
    }
}
