package com.habilisoft.doce.api.scheduler.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2021-07-02.
 */
public class CronUtils {

    public static String getCronExpressionFromTime(List<DayOfWeek> dayOfWeeks, LocalTime localTime) {
        String defaultTimeZone = "America/Santo_Domingo";
        return getCronExpressionFromTime(defaultTimeZone, dayOfWeeks, localTime);
    }

    public static String getCronExpressionFromTime(String timeZone, List<DayOfWeek> dayOfWeeks, LocalTime time) {

        LocalDateTime ldt = time.atDate(LocalDate.now());

        ZonedDateTime userDateTime = ldt.atZone(ZoneId.of(timeZone));

        ZonedDateTime serverTime = userDateTime.withZoneSameInstant(ZoneId.systemDefault());

        int daysDiff = userDateTime.getDayOfYear() - serverTime.getDayOfYear();

        if(daysDiff > 0) {
            dayOfWeeks = dayOfWeeks.stream()
                    .map(CronUtils::getPreviousDay)
                    .collect(Collectors.toList());
        } else if(daysDiff < 0) {
            dayOfWeeks = dayOfWeeks.stream()
                    .map(CronUtils::getNextDay)
                    .collect(Collectors.toList());
        }


        return String.format(
                "0 %s %s ? * %s",
                serverTime.format(DateTimeFormatter.ofPattern("mm")),
                serverTime.format(DateTimeFormatter.ofPattern("H")),
                dayOfWeeks.stream().map(day -> day.toString().substring(0, 3)).collect(Collectors.joining(",")));

    }

    private static DayOfWeek getPreviousDay(DayOfWeek day) {
        if(day.getValue() == 1) {
            return DayOfWeek.SUNDAY;
        }
        return DayOfWeek.of(day.getValue() - 1);
    }

    private static DayOfWeek getNextDay(DayOfWeek day) {
        if(day.getValue() == 7) {
            return DayOfWeek.MONDAY;
        }
        return DayOfWeek.of(day.getValue() + 1);
    }
}
