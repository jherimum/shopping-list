package com.eugenio.shopping_list_app.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RestException.NotFoundException.class})
    ResponseEntity<Object> handleConflict(RestException.NotFoundException ex, WebRequest request) {
        ProblemDetail detail = createProblemDetail(ex, HttpStatus.NOT_FOUND, ex.getMessage(), null, null, request);
        return super.handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
