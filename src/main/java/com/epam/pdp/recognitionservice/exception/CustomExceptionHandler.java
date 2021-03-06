package com.epam.pdp.recognitionservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ThereIsNoSuchReportException.class)
    protected ResponseEntity<CustomException> handleThereIsNoSuchReportException() {
        return new ResponseEntity<>(new CustomException("The report does not exist or is not yet ready."), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private class CustomException {
        private String message;
    }
}
