package com.habilisoft.doce.api.email.services;

import java.io.IOException;

public interface TemplateFetcher {
    String getEmailTemplate(final String templateName) throws IOException;
}
