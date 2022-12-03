package com.armando.timeattendance.api.scheduler.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created on 2020-11-18.
 */
@Component
public class ScheduleLoader implements CommandLineRunner {
    private final ReportScheduler notificationScheduler;

    public ScheduleLoader(ReportScheduler notificationScheduler) {
        this.notificationScheduler = notificationScheduler;
    }

    @Override
    public void run(String... args) throws Exception {
        notificationScheduler.loadReminders();
    }
}
