package com.habilisoft.doce.api.domain.exceptions;

import com.habilisoft.doce.api.auth.base.exceptions.RestResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created on 11/11/22.
 */
@RequiredArgsConstructor
public class EmployeeNotFoundException extends RestResponseException {
    private final Long employeeId;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{employeeId};
    }
}