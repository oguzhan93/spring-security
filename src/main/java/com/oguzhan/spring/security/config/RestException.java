package com.oguzhan.spring.security.config;

import com.oguzhan.spring.security.dtos.ErrorDto;
import com.oguzhan.spring.security.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestException {
    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorDto(exception.getMessage()));
    }
}
