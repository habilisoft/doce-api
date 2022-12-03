package com.armando.timeattendance.api.queue;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 2021-11-03.
 */
@Slf4j
@Component
public class SqsQueueSender {
    private final QueueMessagingTemplate queueMessagingTemplate;
    private final String queueName;

    public SqsQueueSender(final AmazonSQSAsync amazonSQSAsync,
                          final @Value("${cloud.aws.sqs.device-queue}") String queueName) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
        this.queueName = queueName;
    }

    public void send(String message) {
        this.queueMessagingTemplate.send(queueName, MessageBuilder.withPayload(message).build());
    }

    public void convertAndSend(Object o) {
       log.info("Sending message to queue {} {}", kv("queueName", queueName), kv("message", o));
       this.queueMessagingTemplate.convertAndSend(queueName, o);
    }
}
