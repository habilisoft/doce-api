package com.armando.timeattendance.api.tenant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Daniel
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class TenantDuplicatedException extends RuntimeException {

    public TenantDuplicatedException(String message) {
        super(message);
    }

}
