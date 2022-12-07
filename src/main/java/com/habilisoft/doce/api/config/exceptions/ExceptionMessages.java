package com.habilisoft.doce.api.config.exceptions;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created on 8/30/22.
 */
@Component
public class ExceptionMessages {
    private final MessageSource messageSource;

    public ExceptionMessages(final @Qualifier("exceptionsMessageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Object... args) {
        return  messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code) {
        return getMessage(code, (Object) null);
    }
}
