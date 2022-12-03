package com.armando.timeattendance.api.auth.base.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created on 2020-08-11.
 */
public class InvalidFieldException extends RestResponseException {
    private final String entity;
    private final String field;

    public InvalidFieldException(String entity, String field) {
        this.entity = entity;
        this.field = field;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{field, entity};
    }
}
