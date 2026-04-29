package com.habilisoft.doce.api.email.services;

import com.habilisoft.doce.api.email.models.Attachment;
import com.habilisoft.doce.api.email.models.EmailRequest;
import com.habilisoft.doce.api.email.models.PlainTextEmailRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.util.ByteArrayDataSource;
import java.util.Date;

@Service
@ConditionalOnExpression("'${mail.enabled:true}' == 'true' and '${mail.sender:}' == 'JAVA'")
public class DefaultMailService implements MailService {
    private final JavaMailSender mailSender;

    public DefaultMailService(final JavaMailSender mailSender) {

        this.mailSender = mailSender;
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

            String subject = request.getSubject();

            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(text, true);
            message.setFrom("Reportes de Asistencia <reportes@doce.do>");

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

}
