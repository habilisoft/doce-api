package com.habilisoft.doce.api.persistence.repositories.reports;

import com.habilisoft.doce.api.utils.DateUtils;

import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created on 18/12/22.
 */
public interface LateArrivalResponse {
    Long getEmployee_id();
    Integer getEnroll_id();
    String getEmployee_name();
    String getGroup_name();
    String getWork_shift();
    Date getRecord_date();
    Date getRecord_time();
    Long getDifference_in_seconds();

    default String getDifference() {
        return DateUtils.secondsToSecondsMinutesAndHours(getDifference_in_seconds());
    }

    default String getHour() {
        return DateUtils.getTime(getRecord_time())
                .format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    default String getDateString() {
        return getRecord_date().toString();
    }
}
