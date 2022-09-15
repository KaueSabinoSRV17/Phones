package com.api.phones.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Error.Field> fields = new ArrayList<>();

        ex.getAllErrors()
            .stream()
            .forEach(error -> {
                fields.add(new Error.Field(
                    ((FieldError) error).getField(), // Label of each field on the returned Error
                    messageSource.getMessage(error, LocaleContextHolder.getLocale()))); // Message of each field on the returned Error (Locale By Country)
            });

        var error = new Error(
            status.value(),
            "You may not have given the neceessary informations properly",
            LocalDateTime.now(),
            fields);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

}
