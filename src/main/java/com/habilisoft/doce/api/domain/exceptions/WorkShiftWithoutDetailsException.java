package com.habilisoft.doce.api.domain.exceptions;

import com.habilisoft.doce.api.auth.base.exceptions.RestResponseException;
import org.springframework.http.HttpStatus;

/**
 * Created on 2/12/22.
 */
public class WorkShiftWithoutDetailsException extends RestResponseException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
