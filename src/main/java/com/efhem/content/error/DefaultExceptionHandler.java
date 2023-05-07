package com.efhem.content.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleAuthenticationException(Exception ex) {

        var httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorMessage re = new ErrorMessage(httpStatus, "Authentication fail");
        return ResponseEntity.status(httpStatus).body(re);
    }
}