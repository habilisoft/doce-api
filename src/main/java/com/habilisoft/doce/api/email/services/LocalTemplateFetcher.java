package com.habilisoft.doce.api.email.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 2020-08-11.
 */
public class LocalTemplateFetcher implements TemplateFetcher {
    @Override
    public String getEmailTemplate(String templateName) throws IOException {
        Resource resource = new ClassPathResource(templateName);
        InputStream input = resource.getInputStream();
        return new String(input.readAllBytes());
    }
}
