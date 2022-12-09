package com.habilisoft.doce.api.email.services;

import com.habilisoft.doce.api.email.models.Attachment;
import com.habilisoft.doce.api.email.models.EmailRequest;
import com.habilisoft.doce.api.email.models.PlainTextEmailRequest;
import com.habilisoft.doce.api.email.models.TemplateSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.util.ByteArrayDataSource;
import java.util.Date;

@Service
@ConditionalOnProperty(name = "mail.sender", havingValue = "JAVA")
public class DefaultMailService implements MailService {
    private final JavaMailSender mailSender;
    private final Environment environment;

    public DefaultMailService(final JavaMailSender mailSender,
                              final Environment environment) {

        this.mailSender = mailSender;
        this.environment = environment;
    }

    @Override
    public void sendMessage(final EmailRequest request) {

        final String text = parseTemplate(request.getTemplate(), request.getModel());
        sendMessage(request, text);

    }

    @Override
    public void sendPlainTextMessage(final PlainTextEmailRequest request){
        sendMessage(request.toEmailRequest(), request.getText());
    }

    private void sendMessage(EmailRequest request, String text){
        final MimeMessagePreparator preparator = mimeMessage -> {

            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

            message.setTo(request.getTo().toArray(new String[0]));

            if(!CollectionUtils.isEmpty(request.getCco())){
                message.setCc(request.getCco().toArray(new String[0]));
            }

            String subject = getSubject(request.getSubject());

            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(text, true);

            if(!CollectionUtils.isEmpty(request.getAttachments())){
                for (Attachment attachment : request.getAttachments()) {
                    message.addAttachment(
                            attachment.getName(),
                            new ByteArrayDataSource(attachment.getInputStream(),
                                    new MimetypesFileTypeMap().getContentType(attachment.getName())));
                }
            }
        };

        mailSender.send(preparator);
    }


    private String getSubject(String subject) {

        String env = environment.getActiveProfiles()[0];
        if("prod".equals(env)) {
            return subject;
        }

        return String.format("%s - %s", subject, StringUtils.capitalize(env));
    }

}
