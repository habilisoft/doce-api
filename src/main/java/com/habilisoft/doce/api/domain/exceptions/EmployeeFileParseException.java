package com.habilisoft.doce.api.domain.exceptions;

import com.habilisoft.doce.api.auth.base.exceptions.RestResponseException;
import org.springframework.http.HttpStatus;

/**
 * Created on 28/11/22.
 */
public class EmployeeFileParseException extends RestResponseException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
