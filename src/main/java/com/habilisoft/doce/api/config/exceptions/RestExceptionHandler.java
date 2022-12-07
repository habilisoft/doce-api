package com.habilisoft.doce.api.config.exceptions;

import com.habilisoft.doce.api.auth.base.exceptions.RestResponseException;
import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.habilisoft.doce.api.logging.LogEvents;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Created on 2020-07-03.
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    private final ExceptionMessages messages;
    private final String maxFileSize;

    @Autowired
    public RestExceptionHandler(ExceptionMessages messages, @Value("${spring.servlet.multipart.max-file-size}") final String maxFileSize) {
        this.messages = messages;
        this.maxFileSize = maxFileSize;
    }

    @ExceptionHandler(RestResponseException.class)
    public @ResponseBody
    ResponseEntity<GlobalExceptionResponse> handleRestResponseException(HttpServletRequest request, RestResponseException ex) {
        String message = messages.getMessage(String.format("%s.message",ex.getClass().getName()),ex.getParams());
        String code = messages.getMessage(String.format("%s.code",ex.getClass().getName()));

        GlobalExceptionResponse response = GlobalExceptionResponse.builder()
                .message(message)
                .timestamp(new Date())
                .path(request.getRequestURI())
                .code(code)
                .status(ex.getStatus().value())
                .build();

        log.error(
                message,
                kv("eventName", LogEvents.CONTROLLED_EXCEPTION),
                kv("httpStatusCode", ex.getStatus()),
                kv("path", request.getRequestURI()),
                kv("exceptionCode", code),
                kv("exceptionMessage", message),
                kv("exceptions", ex.getParams()),
                kv("tenant", TenantContext.getCurrentTenant()),
                kv("httpStatus", ex.getStatus().getReasonPhrase()),
                ex
        );

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ResponseBody
    @ExceptionHandler({MultipartException.class, MaxUploadSizeExceededException.class, FileUploadException.class, SizeLimitExceededException.class})
    public ResponseEntity<GlobalExceptionResponse> handleSizeLimitException(final HttpServletRequest request, Locale locale) {
        GlobalExceptionResponse response = GlobalExceptionResponse.builder()
                .message(messages.getMessage(
                        "exceptions.fileSizeExceeded",
                        new Object[]{
                                maxFileSize
                        },
                        locale))
                .timestamp(new Date())
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalExceptionResponse> handleException(final HttpServletRequest request,
                                                                   final Exception ex) {
        String uuid = UUID.randomUUID().toString();
        String message = messages.getMessage("java.lang.Exception.message", uuid);
        GlobalExceptionResponse response = GlobalExceptionResponse.builder()
                .message(message)
                .timestamp(new Date())
                .path(request.getRequestURI())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        log.error(
                message,
                kv("eventName", LogEvents.UNCONTROLLED_EXCEPTION),
                kv("httpStatusCode", HttpStatus.INTERNAL_SERVER_ERROR.value()),
                kv("path", request.getRequestURI()),
                kv("exceptionMessage", ex.getMessage()),
                kv("exceptionCause", ex.getCause()),
                kv("exceptionCode", uuid),
                kv("tenant", TenantContext.getCurrentTenant()),
                ex
        );
        if(log.isTraceEnabled()) {
            ex.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
