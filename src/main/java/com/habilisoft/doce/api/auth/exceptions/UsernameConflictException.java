package com.habilisoft.doce.api.auth.exceptions;

import com.habilisoft.doce.api.auth.base.exceptions.RestResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class UsernameConflictException extends RestResponseException {
    private final String username;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{username};
    }
}
