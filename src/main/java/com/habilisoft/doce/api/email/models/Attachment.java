package com.habilisoft.doce.api.email.models;

import lombok.Builder;
import lombok.Data;

import java.io.ByteArrayInputStream;

/**
 * Created on 2019-02-12.
 */
@Data
@Builder
public class Attachment {
    private String name;
    private ByteArrayInputStream inputStream;
}
