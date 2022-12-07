package com.habilisoft.doce.api.scheduler.services;

import com.habilisoft.doce.api.scheduler.model.ScheduledReport;
import com.habilisoft.doce.api.scheduler.model.SendReportTask;
import com.habilisoft.doce.api.persistence.scheduler.repositories.SendReportTaskRepository;
import com.habilisoft.doce.api.scheduler.util.CronUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * Created on 2020-11-19.
 */
@Service
@RequiredArgsConstructor
public class DefaultSendReportTaskService {
    private final SendReportTaskRepository sendReportTaskRepository;

    public List<SendReportTask> createReminders(ScheduledReport scheduledReport) {
        List<SendReportTask> reminders = getCronExpressionsFromScheduledReport(scheduledReport)
                .stream()
                .map(cronExpression ->
                        SendReportTask.builder()
                                .cronExpression(cronExpression)
                                .scheduledReport(scheduledReport)
                                .build())
                .collect(Collectors.toList());

        return sendReportTaskRepository.save(reminders);
    }

    @Transactional
    public void deleteReminders(ScheduledReport scheduledReport) {
        sendReportTaskRepository.deleteByScheduledReport(scheduledReport);

    }

    @Transactional
    public List<SendReportTask> updateReminders(ScheduledReport scheduledReport) {
        sendReportTaskRepository.deleteByScheduledReport(scheduledReport);
        return createReminders(scheduledReport);
    }

    @Transactional
    public List<SendReportTask> getSendReportTasks(ScheduledReport scheduledReport) {
        return sendReportTaskRepository.getTasks(scheduledReport);
    }

    private List<String> getCronExpressionsFromScheduledReport(ScheduledReport report) {
        return report.getScheduleEntries()
                .stream()
                .map(entry -> CronUtils.getCronExpressionFromTime(entry.getDays(), entry.getTime()))
                .collect(Collectors.toList());
    }

    private String getLatestTimezone(List<String> timezones) {
        return timezones.stream()
                .map(TimeZone::getTimeZone)
                .min(Comparator.comparing(TimeZone::getRawOffset))
                .orElse(TimeZone.getTimeZone(timezones.get(0)))
                .getID();
    }

}
