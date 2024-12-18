package com.habilisoft.doce.api.domain.exceptions;

import com.habilisoft.doce.api.auth.base.exceptions.RestResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created on 25/11/22.
 */
@RequiredArgsConstructor
public class DeviceNotFoundException extends RestResponseException {
    private final String serialNumber;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{serialNumber};
    }
}
