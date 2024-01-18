package com.scaler.BlogapiApplication.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    @Getter
    private final String message;
    @Getter
    private final HttpStatus status;
    @Getter
    private final LocalDateTime timestamp;
    public ErrorResponseDto(HttpStatus status,String message,LocalDateTime timestamp){
        this.status=status;
        this.message=message;
        this.timestamp=timestamp;
    }

}
