package com.PrimeFaces.demo.rest.config;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        ResponseEntity<Object> responseEntity = super.handleMethodArgumentNotValid(ex, headers, status, request);
        RestError restError = new RestError();
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<RestSubError> restSubErrors = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            restSubErrors.add(new RestSubError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
        }

 /*       restError.setStatus(status.value());
        restError.setTimestamp(new Date());
        restError.setError("");
        restError.setException("");
        restError.setPath("");*/
        restError.setRestSubErrors(restSubErrors);

        return responseEntity;
    }


}
