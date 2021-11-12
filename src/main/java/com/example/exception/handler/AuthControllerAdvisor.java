package com.example.exception.handler;

import com.example.exception.InvalidPasswordException;
import com.example.exception.InvalidTokenException;
import com.example.exception.UsernameTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvisor {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> loginExceptionHandler(AuthenticationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<Object> usernameTakenHandler(UsernameTakenException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> invalidPasswordHandler(InvalidPasswordException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> tokenExpiredHandler(InvalidTokenException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
