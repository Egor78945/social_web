package com.example.socialweb.controllers.advice;

import com.example.socialweb.annotations.customExceptionHandlers.UserControllersExceptionHandler;
import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.exceptions.WrongDataException;
import com.example.socialweb.exceptions.WrongFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = UserControllersExceptionHandler.class)
public class UserControllersAdvice {
    @ExceptionHandler(RequestCancelledException.class)
    public ResponseEntity<String> requestCancelledExceptionHandler(RequestCancelledException e) {
        return ResponseEntity.ok(e.getMessage());
    }

    @ExceptionHandler(WrongDataException.class)
    public ResponseEntity<String> WrongDataExceptionHandler(WrongDataException e) {
        return ResponseEntity.ok(e.getMessage());
    }

    @ExceptionHandler(WrongFormatException.class)
    public ResponseEntity<String> WrongFormatExceptionHandler(WrongFormatException e) {
        return ResponseEntity.ok(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> BadCredentialsExceptionHandler(BadCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
