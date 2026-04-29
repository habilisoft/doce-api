package com.habilisoft.doce.api.email.services;

import com.habilisoft.doce.api.email.models.EmailRequest;
import com.habilisoft.doce.api.email.models.PlainTextEmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(name = "mail.enabled", havingValue = "false")
public class DisabledMailService implements MailService {

    @Override
    public void sendMessage(final EmailRequest request) {
        log.warn("Mail service is disabled. Skipping email with subject '{}' to {}",
                request.getSubject(),
                request.getTo());
    }

    @Override
    public void sendPlainTextMessage(final PlainTextEmailRequest request) {
        log.warn("Mail service is disabled. Skipping plain text email with subject '{}' to {}",
                request.getSubject(),
                request.getTo());
    }
}
