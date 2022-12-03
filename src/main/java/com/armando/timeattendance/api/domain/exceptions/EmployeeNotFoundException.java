package com.armando.timeattendance.api.domain.exceptions;

import com.armando.timeattendance.api.auth.base.exceptions.RestResponseException;
import com.armando.timeattendance.api.domain.model.Employee;
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
