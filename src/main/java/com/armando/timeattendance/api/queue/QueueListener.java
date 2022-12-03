package com.armando.timeattendance.api.queue;

import com.armando.timeattendance.api.services.QueueEventsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;
import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 2021-11-02.
 */
@Slf4j
@Component
public class QueueListener {
    private final QueueEventsService queueEventsService;

    public QueueListener(QueueEventsService queueEventsService) {
        this.queueEventsService = queueEventsService;
    }

    @SqsListener("${cloud.aws.sqs.api-queue}")
    public void onMessage(String message) {
        log.info("Received message from queue {}", kv("message", message) );
        queueEventsService.processCommand(message);
    }

}
