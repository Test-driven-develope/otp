package com.example.otp.exception;

import com.example.otp.resource.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleBadRequestException(BadRequestException exception) {
        return ResponseEntity.builder().message(exception.getMessage()).build();
    }
    
    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleEntityNotExistException(EntityNotExistException exception) {
        return ResponseEntity.builder().message(exception.getMessage()).build();
    }
    
    @ExceptionHandler(SystemExceptions.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleSystemExceptions(SystemExceptions exceptions) {
        return ResponseEntity.builder().message(exceptions.getMessage()).build();
    }
    
}
