package com.habilisoft.doce.api.email.services;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.habilisoft.doce.api.email.models.EmailRequest;
import com.habilisoft.doce.api.email.models.PlainTextEmailRequest;
import com.habilisoft.doce.api.email.models.TemplateSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.Properties;

/**
 * Created on 2019-04-08.
 */
@Component
@ConditionalOnProperty(name = "mail.sender", havingValue = "SES")
public class SESMailService implements MailService {
    private final String awsRegion;
    private final String fromAddress;
    private final Logger logger = LoggerFactory.getLogger(SESMailService.class);
    private final AWSCredentialsProvider credentialsProvider;
    private final Environment environment;

    public SESMailService(@Value("${cloud.aws.region.static}") final String awsRegion,
                          @Value("${mail.ses.fromAddress}") final String fromAddress,
                          final Environment environment,
                          AWSCredentialsProvider credentialsProvider) {
        this.awsRegion = awsRegion;
        this.fromAddress = fromAddress;
        this.credentialsProvider = credentialsProvider;
        this.environment = environment;
    }

    private AmazonSimpleEmailService getClient(){

        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(awsRegion).build();

    }


    @Override
    public void sendPlainTextMessage(PlainTextEmailRequest request) {
/*
        if(true){
            return;
        }*/

        String message = request.getText();

        AmazonSimpleEmailService client = getClient();

        SendEmailRequest req = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(request.getTo()))
                .withMessage(
                        new com.amazonaws.services.simpleemail.model.Message()
                                .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(message)))
                                .withSubject(new Content().withCharset("UTF-8").withData(getSubject(request.getSubject())))
                ).withSource(Optional.ofNullable(request.getFromAddress()).orElse(fromAddress));

        client.sendEmail(req);
    }

    public void sendMessage(EmailRequest request) {
        try {
            String bodyHtml = parseTemplate(request.getTemplate(), request.getModel());
            Session session = Session.getDefaultInstance(new Properties());

            MimeMessage message = new MimeMessage(session);


            Address[] addresses = new Address[request.getTo().size()];

            for(int i = 0; i < request.getTo().size() ; i++){
                InternetAddress ia = new InternetAddress(request.getTo().get(i));
                addresses[i] = ia;
            }


            message.setSubject(getSubject(request.getSubject()), "UTF-8");
            //message.setFrom(Optional.ofNullable(request.getFromAddress()).orElse(fromAddress));

            if(!CollectionUtils.isEmpty(request.getCco())) {

                Address[] cc = new Address[request.getCco().size()];

                for(int i = 0; i < request.getCco().size() ; i++){
                    InternetAddress ia = new InternetAddress(request.getCco().get(i));
                    cc[i] = ia;
                }

                message.setRecipients(Message.RecipientType.BCC, cc);
            }

            message.setFrom(Optional.ofNullable(request.getFromAddress()).orElse("MyCheckins <support@mycheckins.io>"));
            message.setRecipients(Message.RecipientType.TO, addresses);

            MimeMultipart msg_body = new MimeMultipart("alternative");
            MimeBodyPart wrap = new MimeBodyPart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(bodyHtml, "text/html; charset=UTF-8");
            msg_body.addBodyPart(htmlPart);

            wrap.setContent(msg_body);
            MimeMultipart msg = new MimeMultipart("mixed");
            message.setContent(msg);

            msg.addBodyPart(wrap);

            if(request.getAttachments()!=null && request.getAttachments().size()>0) {
                request.getAttachments().forEach(attachment -> {
                    try {

                        MimeBodyPart att = new MimeBodyPart();
                        DataSource fds = new ByteArrayDataSource(attachment.getInputStream(), new MimetypesFileTypeMap().getContentType(attachment.getName()));
                        att.setDataHandler(new DataHandler(fds));
                        att.setFileName(attachment.getName());
                        msg.addBodyPart(att);

                    } catch (IOException | MessagingException ex) {
                        logger.error("Could not add attachment to email, exception is: " + ex.getMessage());
                    }
                });
            }

            AmazonSimpleEmailService client = getClient();


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);

            RawMessage rawMessage =
                    new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

            SendRawEmailRequest rawEmailRequest =
                    new SendRawEmailRequest(rawMessage);

            rawEmailRequest.setSource("MyCheckins <support@mycheckins.io>");

            client.sendRawEmail(rawEmailRequest);

        } catch (MessagingException | IOException ex) {
            logger.error("Failed to send email." + ex);

            ex.printStackTrace();
        }
    }

    private String getSubject(String subject) {

        String env = environment.getActiveProfiles()[0];
        if("prod".equals(env)) {
            return subject;
        }

        return String.format("%s - %s", subject, StringUtils.capitalize(env));
    }
}
