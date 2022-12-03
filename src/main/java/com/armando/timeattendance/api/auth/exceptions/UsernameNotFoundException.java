package com.armando.timeattendance.api.auth.exceptions;

import com.armando.timeattendance.api.auth.base.exceptions.RestResponseException;
import org.springframework.http.HttpStatus;

/**
 * Created on 2021-01-13.
 */
public class UsernameNotFoundException extends RestResponseException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
