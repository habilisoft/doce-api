package com.habilisoft.doce.api.email.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
        private EmailTemplates template;
        private List<String> to;
        private List<String> cco;
        private Map<String, Object> model;
        private String subject;
        private List<Attachment> attachments;
        private String fromAddress;
}
