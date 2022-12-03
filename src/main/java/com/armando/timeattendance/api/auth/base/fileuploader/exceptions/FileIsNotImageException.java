package com.armando.timeattendance.api.auth.base.fileuploader.exceptions;

import com.armando.timeattendance.api.auth.base.exceptions.RestResponseException;
import org.springframework.http.HttpStatus;

/**
 * Created on 2020-09-27.
 */
public class FileIsNotImageException extends RestResponseException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
