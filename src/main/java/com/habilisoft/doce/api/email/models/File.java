package com.habilisoft.doce.api.email.models;

import lombok.Data;

@Data
public class File {
    private String contentType;
    private Long contentLength;
    private byte[] content;
}
