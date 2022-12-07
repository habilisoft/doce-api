package com.habilisoft.doce.api.config.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalExceptionResponse implements Serializable {
    private int status;
    private String message;
    private String error;
    private Date timestamp;
    private String path;
    private String code;
}
