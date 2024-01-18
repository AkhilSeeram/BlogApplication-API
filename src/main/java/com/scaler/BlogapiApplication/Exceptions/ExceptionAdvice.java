package com.scaler.BlogapiApplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(
            {
                    ArticleNotFoundException.class,
                    UsernameNotFoundException.class
            })
    public ResponseEntity<Object> handleNotFoundException(Exception e){
        ErrorResponseDto errorResponseDto=new ErrorResponseDto(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now() );
        return new ResponseEntity<>(errorResponseDto,errorResponseDto.getStatus());
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(Exception e){
        ErrorResponseDto errorResponseDto=new ErrorResponseDto(HttpStatus.UNAUTHORIZED,"invalid credentials or session expired",LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto,errorResponseDto.getStatus());
    }
}
