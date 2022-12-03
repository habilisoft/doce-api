package com.armando.timeattendance.api.domain.exceptions;

import com.armando.timeattendance.api.auth.base.exceptions.RestResponseException;
import com.armando.timeattendance.api.domain.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created on 29/11/22.
 */
@RequiredArgsConstructor
public class NoDeviceInLocationException extends RestResponseException {
    private final Location location;
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{location.getName()};
    }
}
