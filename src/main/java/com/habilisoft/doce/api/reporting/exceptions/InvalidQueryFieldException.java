package com.habilisoft.doce.api.reporting.exceptions;

import com.habilisoft.doce.api.auth.base.exceptions.RestResponseException;
import org.springframework.http.HttpStatus;

/**
 * Created on 20/1/23.
 */
public class InvalidQueryFieldException extends RestResponseException {
    private final String field;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public InvalidQueryFieldException(String field) {
        this.field = field;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{field};
    }
}
