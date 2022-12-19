package com.habilisoft.doce.api.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created on 2021-07-02.
 */
@Slf4j
public class DateUtils {
    public static String getCronExpressionFromTime(String timeZone, List<DayOfWeek> dayOfWeeks, LocalTime time) {
        LocalDateTime ldt = time.atDate(LocalDate.now());
        ZonedDateTime userDateTime = ldt.atZone(ZoneId.of(timeZone));
        ZonedDateTime serverTime = userDateTime.withZoneSameInstant(ZoneId.systemDefault());

        return String.format(
                "0 %s %s ? * %s",
                serverTime.format(DateTimeFormatter.ofPattern("mm")),
                serverTime.format(DateTimeFormatter.ofPattern("H")),
                dayOfWeeks.stream().map(day -> day.toString().substring(0, 3)).collect(Collectors.joining(",")));
    }

    public static ZonedDateTime getCurrentDateInTimezone(String timezone){
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of(timezone));
        return ldt.atZone(ZoneId.of(timezone));
    }

    public static LocalTime getTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault()).toLocalTime();
    }

    public static LocalDate getDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(),
                ZoneId.systemDefault());
    }

    public static Long secondsToMinutes(Long seconds) {
        return seconds / 60L;
    }

    public static Date firstDayOfWeek(Date date) {
        if(date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        return calendar.getTime();
    }

    public static Date dayOfWeek(Date date, int dayOfWeek) {
        if(date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar.getTime();
    }

    public static Date getDateInUtc(String timeZone) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    .parse(DateUtils.getCurrentDateInTimezone(timeZone)
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } catch (ParseException e) {
            log.error("Error getting answer date in UTC", e);
            return new Date();
        }
    }

    public static String secondsToSecondsMinutesAndHours(Long totalSecs) {
        Long hours = totalSecs / 3600;
        Long minutes = (totalSecs % 3600) / 60;
        Long seconds = totalSecs % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
