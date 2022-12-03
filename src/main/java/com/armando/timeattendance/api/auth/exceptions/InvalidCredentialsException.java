package com.armando.timeattendance.api.auth.exceptions;

import com.armando.timeattendance.api.auth.base.exceptions.RestResponseException;
import org.springframework.http.HttpStatus;

/**
 * Created on 2020-08-03.
 */
public class InvalidCredentialsException extends RestResponseException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
