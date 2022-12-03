package com.armando.timeattendance.api.domain.exceptions;

import com.armando.timeattendance.api.auth.base.exceptions.RestResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created on 11/11/22.
 */
@RequiredArgsConstructor
public class DocumentNumberConflictException extends RestResponseException {
    private final String documentNumber;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_ACCEPTABLE;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{documentNumber};
    }
}
