package com.armando.timeattendance.api.domain.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getTime24H(Date date){
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    public static String getMMDDYYYY(Date date){
        return new SimpleDateFormat("MM-dd-yyyy").format(date);
    }

    public static String getMMMDD(Date date){
        return new SimpleDateFormat("MMM dd").format(date);
    }

    public static String getDayName(final Date date) {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
        return simpleDateformat.format(date);
    }

    public static Date addTimeUnit(final Date date, int unit, int unitTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit, unitTime);

        return calendar.getTime();
    }

    public static Date addMinutes(final Date date, int minutes) {
        return addTimeUnit(date, Calendar.MINUTE, minutes);
    }

    public static Date addMinutesWithNow(final Date date, int minutes) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        cal.set(Calendar.HOUR, calendar.get(Calendar.HOUR));
        cal.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, calendar.get(Calendar.SECOND));

        return addTimeUnit(cal.getTime(), Calendar.MINUTE, minutes);
    }

    public static Boolean between(Date date, Date start, Date end) {
        return start.compareTo(date) <= 0 && end.compareTo(date) >= 0;
    }

    public static Date parse(final String date) throws ParseException {
        return sdf.parse(date);
    }

}
