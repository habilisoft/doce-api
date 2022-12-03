package com.armando.timeattendance.api.domain.exceptions;

import com.armando.timeattendance.api.auth.base.exceptions.RestResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created on 11/11/22.
 */
@RequiredArgsConstructor
public class EnrollIdNotFoundException extends RestResponseException {
    private final Integer enrollId;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{enrollId};
    }
}
