package com.komarov.webschool.controller;

import com.komarov.webschool.exception.ExtraInformationException;
import com.komarov.webschool.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handlerNotFoundException(NotFoundException e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ExtraInformationException.class)
    public ResponseEntity<String> handlerExtraInformationException(ExtraInformationException e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }
}
