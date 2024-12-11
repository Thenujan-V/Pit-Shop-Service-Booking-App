package com.example.booking.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ComplexValidationException extends RuntimeException{
    private final String fieldName;
    private final String errorCode;
    private final HttpStatus httpStatus;

    public ComplexValidationException(String fieldName, String errorCode, HttpStatus httpStatus) {
        super(String.format("Validation failed for field '%s': %s", fieldName, errorCode));
        this.fieldName = fieldName;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
