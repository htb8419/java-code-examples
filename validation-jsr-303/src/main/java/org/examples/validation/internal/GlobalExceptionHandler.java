package org.examples.validation.internal;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), getMessage(error.getDefaultMessage()))
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private String getMessage(String code, Object... args) {
        if (!StringUtils.hasText(code)) {
            return code;
        }
        try {
            return messageSource.getMessage(code, args, getCurrentLocale());
        } catch (NoSuchMessageException ignored) {
            return String.format(code, args);
        }
    }

    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }
}
