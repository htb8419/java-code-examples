package org.examples;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.TimeoutException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<?> timeoutException(){
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("timeout");
    }
}
