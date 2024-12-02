package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerError {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
         Map<String, String> errorMap = new HashMap<>();
         ex.getBindingResult().getFieldErrors().forEach(error -> {
             errorMap.put(error.getField(), error.getDefaultMessage());
         });
         return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    public ObjectError handleAlreadyExists(AlreadyExistsException ex) {
        ObjectError error = new ObjectError();
        error.setTimestamp(new Date());
        error.setMessage(ex.getMessage());
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return error;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ObjectError handPageNotFound() {
        ObjectError error = new ObjectError();
        error.setTimestamp(new Date());
        error.setMessage("Page not found");
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ObjectError NotFoundException(NotFoundException ex) {
        ObjectError error = new ObjectError();
        error.setTimestamp(new Date());
        error.setMessage(ex.getMessage());
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        return error;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateKeyException.class)
    public ObjectError DuplicateKeyExp(DuplicateKeyException ex) {
        ObjectError error = new ObjectError();
        error.setTimestamp(new Date());
        error.setMessage(ex.getMessage());
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return error;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ForeignKeyException.class)

    public ObjectError ForeignKeyException(ForeignKeyException ex) {
        ObjectError error = new ObjectError();
        error.setTimestamp(new Date());
        error.setMessage(ex.getMessage());
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return error;
    }
}
