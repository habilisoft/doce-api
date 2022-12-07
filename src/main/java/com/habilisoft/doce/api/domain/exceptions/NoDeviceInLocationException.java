package com.habilisoft.doce.api.domain.exceptions;

import com.habilisoft.doce.api.auth.base.exceptions.RestResponseException;
import com.habilisoft.doce.api.domain.model.Location;
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
