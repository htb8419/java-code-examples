package org.examples.endpoint;

import org.examples.exception.BusinessException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {
    final MessageSource messageSource;

    public DefaultExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        // You can customize the response as needed
        return new ResponseEntity<>(ErrorResponse..build(messageSource, LocaleContextHolder.getLocale()), HttpStatus.BAD_REQUEST);
    }

}
