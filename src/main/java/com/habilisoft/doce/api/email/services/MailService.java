package com.habilisoft.doce.api.email.services;


import com.habilisoft.doce.api.email.models.EmailRequest;
import com.habilisoft.doce.api.email.models.EmailTemplates;
import com.habilisoft.doce.api.email.models.PlainTextEmailRequest;
import com.habilisoft.doce.api.email.models.TemplateSource;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created on 2019-04-08.
 */
public interface MailService {

    default String parseTemplate(final String templateName, final Map<String, Object> model) {
        try {
            String template = new LocalTemplateFetcher().getEmailTemplate(templateName);

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
            cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
            cfg.setTemplateLoader(new ClassTemplateLoader(MailService.class, "/"));

            Template t = new Template(templateName, new StringReader(template), cfg);
            Writer out = new StringWriter();
            t.process(model, out);
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    default String parseTemplate(final EmailTemplates templateName, final Map<String, Object> model) {
        return parseTemplate(templateName.toString(), model);
    }
    void sendMessage(final EmailRequest request);

    void sendPlainTextMessage(final PlainTextEmailRequest request);

    default void sendMessages(final List<EmailRequest> requests){
        if(requests == null || requests.size() <1){
            return;
        }

        Executors.newSingleThreadExecutor().execute(()->{
            for(EmailRequest request: requests){
                sendMessage(request);
            }
        });

    }
}
