package com.habilisoft.doce.api.email.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created on 2019-03-27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlainTextEmailRequest {

    private String text;
    private List<String> to;
    private String subject;
    private List<Attachment> attachments;
    private String fromAddress;


    public EmailRequest toEmailRequest(){
        EmailRequest request = new EmailRequest();
        BeanUtils.copyProperties(this, request);
        return request;
    }
}

